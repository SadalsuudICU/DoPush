package com.sadalsuud.push.common.constant;

/**
 * @Description 常量信息
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 10/12/2023
 */
public class DoPushConstant {

    /**
     * businessId默认的长度
     * 生成的逻辑：com.java3y.dopush.support.utils.TaskInfoUtils#generateBusinessId(java.lang.Long, java.lang.Integer)
     */
    public static final Integer BUSINESS_ID_LENGTH = 16;
    /**
     * 接口限制 最多的人数
     */
    public static final Integer BATCH_RECEIVER_SIZE = 100;
    /**
     * 消息发送给全部人的标识
     * (企业微信 应用消息)
     * (钉钉自定义机器人)
     * (钉钉工作消息)
     */
    public static final String SEND_ALL = "@all";
    /**
     * 链路追踪缓存的key标识
     */
    public static final String CACHE_KEY_PREFIX = "dopush";
    public static final String MESSAGE_ID = "MessageId";
    /**
     * 消息模板常量；
     * 如果新建模板/账号时，没传入则用该常量
     */
    public static final String DEFAULT_CREATOR = "Sadalsuud";
    public static final String DEFAULT_UPDATOR = "Sadalsuud";
    public static final String DEFAULT_TEAM = "Sadalsuud";
    public static final String DEFAULT_AUDITOR = "Sadalsuud";
    /**
     * 项目打印常量
     */
    public static final String PROJECT_NAME = " :: DoPush :: ";
    public static final String PROJECT_BANNER = "\n" +
            "██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗██╗  ██╗\n" +
            "██╔══██╗██╔═══██╗██╔══██╗██║   ██║██╔════╝██║  ██║\n" +
            "██║  ██║██║   ██║██████╔╝██║   ██║███████╗███████║\n" +
            "██║  ██║██║   ██║██╔═══╝ ██║   ██║╚════██║██╔══██║\n" +
            "██████╔╝╚██████╔╝██║     ╚██████╔╝███████║██║  ██║\n" +
            "╚═════╝  ╚═════╝ ╚═╝      ╚═════╝ ╚══════╝╚═╝  ╚═╝\n";
    private DoPushConstant() {

    }


}
