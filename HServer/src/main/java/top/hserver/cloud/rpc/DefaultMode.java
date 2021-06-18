package top.hserver.cloud.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.hserver.cloud.bean.ServiceData;
import top.hserver.cloud.client.handler.RpcClientHandler;
import top.hserver.cloud.config.AppRpc;
import top.hserver.core.ioc.annotation.Bean;

/**
 * @author hxm
 */
@Bean
public class DefaultMode implements RpcAdapter {

    private static final Logger log = LoggerFactory.getLogger(DefaultMode.class);

    @Override
    public boolean rpcMode(AppRpc appRpc, Integer port) {
        if (appRpc.getMode()==null||appRpc.getMode().trim().length() == 0) {
            String address = appRpc.getAddress();
            if (address != null) {
                ServiceData serviceData = defaultReg(address);
                if (serviceData != null) {
                    RpcClientHandler.reg(serviceData);
                } else {
                    log.error(address + "注册失败");
                }
            }
            return true;
        }
        return false;
    }


    private ServiceData defaultReg(String addressData) throws RuntimeException {
        try {
            String[] split = addressData.split(",");
            for (String s : split) {
                String[] split1 = s.split("@");
                String name = split1[1];
                String[] split2 = split1[0].split(":");
                String address = split2[0];
                String port = split2[1];
                ServiceData serviceData = new ServiceData();
                serviceData.setHost(address);
                serviceData.setPort(Integer.parseInt(port));
                serviceData.setServerName(name);
                return serviceData;
            }
        } catch (Exception e) {
            throw new RuntimeException("格式异常，例子: 127.0.0.1:8888@ServerName");
        }
        return null;
    }
}
