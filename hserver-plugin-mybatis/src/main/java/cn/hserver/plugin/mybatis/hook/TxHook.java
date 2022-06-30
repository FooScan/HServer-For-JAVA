package cn.hserver.plugin.mybatis.hook;

import io.netty.util.concurrent.FastThreadLocal;
import cn.hserver.plugin.mybatis.proxy.MybatisProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hserver.core.interfaces.HookAdapter;
import cn.hserver.core.ioc.annotation.Hook;
import cn.hserver.plugin.mybatis.annotation.Tx;
import java.lang.reflect.Method;

/**
 * @author hxm
 */
@Hook(Tx.class)
public class TxHook implements HookAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TxHook.class);
    private static final FastThreadLocal<Long> TIMEOUT_MILLISECOND = new FastThreadLocal<>();
    public static final FastThreadLocal<Boolean> ISTX = new FastThreadLocal<>();

    @Override
    public void before(Class aClass, Method method, Object[] objects) {
        Tx annotation = method.getAnnotation(Tx.class);
        if (annotation != null) {
            ISTX.set(true);
            if (annotation.timeoutMillisecond() != -1) {
                TIMEOUT_MILLISECOND.set(System.currentTimeMillis());
            }
        }
    }

    @Override
    public Object after(Class aClass, Method method, Object o) {
        try {
            Tx annotation = method.getAnnotation(Tx.class);
            if (annotation != null) {
                try {
                    if (annotation.timeoutMillisecond() != -1) {
                        Long startTime = TIMEOUT_MILLISECOND.get();
                        if (System.currentTimeMillis() - startTime > annotation.timeoutMillisecond()) {
                            //超时了进行回滚
                            try {
                                MybatisProxy.get().rollback();
                                logger.debug("rollback: 超时 {}",System.currentTimeMillis() - startTime > annotation.timeoutMillisecond());
                            } catch (Exception ex) {
                                logger.error(ex.getMessage());
                            }
                        }
                    }
                   MybatisProxy.get().commit();
                } catch (Exception e) {
                    try {
                       MybatisProxy.get().rollback();
                        logger.debug("rollback:{}",e.getMessage());
                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                    }
                }
            }
            return o;
        } finally {
            MybatisProxy.get().close();
            TIMEOUT_MILLISECOND.remove();
            ISTX.remove();
            MybatisProxy.removeSession();
        }
    }

    @Override
    public void throwable(Class aClass, Method method, Throwable throwable) {
        try {
            Tx annotation = method.getAnnotation(Tx.class);
            if (annotation != null) {
                try {
                    //看看是否制定了回滚异常类型
                    Class<? extends Throwable>[] classes = annotation.rollbackFor();
                    if (classes.length != 0) {
                        for (Class<? extends Throwable> aClass1 : classes) {
                            if (throwable.getClass() == aClass1) {
                                MybatisProxy.get().rollback();
                                return;
                            }
                        }
                        MybatisProxy.get().commit();
                        return;
                    }
                    MybatisProxy.get().rollback();
                    logger.debug("rollback:{}",throwable.getMessage());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        } finally {
            MybatisProxy.get().close();
            TIMEOUT_MILLISECOND.remove();
            ISTX.remove();
            MybatisProxy.removeSession();
        }
    }
}
