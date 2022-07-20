package cn.hserver.plugin.web.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.ssl.SslContext;
import io.netty.util.concurrent.EventExecutorGroup;
import cn.hserver.plugin.web.json.JackSonJsonAdapter;
import cn.hserver.plugin.web.json.JsonAdapter;

import java.io.File;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;

/**
 * @author hxm
 */
public class ConstConfig {
    /**
     * 当前项目路径
     */
    public static final String PATH = System.getProperty("user.dir") + File.separator;
    /**
     * 运行环境
     */
    public static Boolean RUNJAR = false;
    /**
     * classpat路径
     */
    public static String CLASSPATH;

    /**
     * 版本号
     */
    public static final String VERSION = "3.0.M3";
    /**
     * 定时任务线程数配置
     */
    public static Integer taskPool = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * SSL 配置
     */
    public static SslContext sslContext = null;

    /**
     * git 地址反馈
     */
    public static final String BUG_ADDRESS = "https://gitee.com/HServer/HServer/issues";

    /**
     * webServer  bossThreadCount
     */
    public static Integer bossPool = 0;

    /**
     * webServer workerGroupThreadCount
     */
    public static Integer workerPool = 0;

    /**
     * backlog 指定了内核为此套接口排队的最大连接个数；
     * 对于给定的监听套接口，内核要维护两个队列: 未连接队列和已连接队列
     * backlog 的值即为未连接队列和已连接队列的和。
     */
    public static Integer backLog = 8192;

    /**
     * 对象处理
     */
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .configure(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

    /**
     * 另外JSON一个名字，兼容以前的
     */
    public static final ObjectMapper JSON = OBJECT_MAPPER;

    /**
     * 通用JSON适配器
     */
    public static JsonAdapter JSONADAPTER = new JackSonJsonAdapter();

    /**
     * 配置文件
     */
    public static String profiles = System.getProperty("env");

    /**
     * 业务线程池子
     */
    public static EventExecutorGroup BUSINESS_EVENT;

    /**
     * 持久化文件存储位置
     */
    public static String PERSIST_PATH = PATH + "queue";

    /**
     * 流量整形
     */
    public static Long WRITE_LIMIT = null;

    public static Long READ_LIMIT = null;

    /**
     * 默认消息大小
     */
    public static Integer HTTP_CONTENT_SIZE = Integer.MAX_VALUE;

    /**
     * 请求ID
     */
    public final static String REQUEST_ID = "hRequestId";


    /**
     * 内部自用名字
     */
    public final static String SERVER_NAME = "HServer";

    /**
     * 用户自定义的服务名
     */
    public static String APP_NAME = "HServer";

    /**
     * hum 默认消息端口
     */
    public static Integer HUM_PORT = 9527;

    /**
     * HServer 端口
     */
    public static Integer[] PORTS = new Integer[]{8888};

    /**
     * 是否开启追踪
     */
    public static Boolean TRACK = false;

    /**
     * 跟踪扩展包
     */
    public static String[] TRACK_EXT_PACKAGES = new String[0];

    /**
     * 不跟踪的包
     */
    public static String[] TRACK_NO_PACKAGES = new String[0];

}