package com.sadalsuud.push.stream.constants;

/**
 * @Description Flink常量信息
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.stream.constants
 */
public class FlinkConstant {
    /**
     * Kafka 配置信息
     * 192.168.249.128 www.dopush.com
     */
    public static final String GROUP_ID = "dopushLogGroup";
    public static final String TOPIC_NAME = "dopushTraceLog";
    public static final String BROKER = "www.dopush.com:9092";
    /**
     * redis 配置
     * 192.168.249.128 www.dopush.com
     */
    public static final String REDIS_IP = "www.dopush.com";
    public static final String REDIS_PORT = "6379";
    public static final String REDIS_PASSWORD = "123456";
    /**
     * Flink流程常量
     */
    public static final String SOURCE_NAME = "dopush_kafka_source";
    public static final String FUNCTION_NAME = "dopush_transfer";
    public static final String SINK_NAME = "dopush_sink";
    public static final String JOB_NAME = "StreamBootStrap";
    private FlinkConstant() {
    }


}