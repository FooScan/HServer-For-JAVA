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
public class WebConstConfig {
    /**
    /**
     * SSL 配置
     */
    public static SslContext sslContext = null;

    /**
     * git 地址反馈
     */
    public static final String BUG_ADDRESS = "https://gitee.com/HServer/HServer/issues";


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
     * 业务线程池子
     */
    public static EventExecutorGroup BUSINESS_EVENT;


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
    public final static String SERVER_NAME = "HServer-Web";


}