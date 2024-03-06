package com.sadalsuud.push.domain.support.cron;

import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.support.cron.entity.XxlJobGroup;
import com.sadalsuud.push.domain.support.cron.entity.XxlJobInfo;
import com.sadalsuud.push.domain.template.MessageTemplate;

/**
 * @Description 定时任务服务接口-领域解耦
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
public interface CronTaskService {
    /**
     * 新增/修改 定时任务
     *
     * @param xxlJobInfo
     * @return 新增时返回任务Id，修改时无返回
     */
    BasicResultVO saveCronTask(XxlJobInfo xxlJobInfo);

    /**
     * 删除定时任务
     *
     * @param taskId
     * @return BasicResultVO
     */
    BasicResultVO deleteCronTask(Integer taskId);

    /**
     * 启动定时任务
     *
     * @param taskId
     * @return BasicResultVO
     */
    BasicResultVO startCronTask(Integer taskId);


    /**
     * 暂停定时任务
     *
     * @param taskId
     * @return BasicResultVO
     */
    BasicResultVO stopCronTask(Integer taskId);


    /**
     * 得到执行器Id
     *
     * @param appName
     * @param title
     * @return BasicResultVO
     */
    BasicResultVO getGroupId(String appName, String title);

    /**
     * 创建执行器
     *
     * @param xxlJobGroup
     * @return BasicResultVO
     */
    BasicResultVO createGroup(XxlJobGroup xxlJobGroup);

    /**
     * 构建xxlJobInfo信息
     *
     * @param messageTemplate
     * @return
     */
    XxlJobInfo buildXxlJobInfo(MessageTemplate messageTemplate);

    /**
     * 根据就配置文件的内容获取jobGroupId，没有则创建
     *
     * @return
     */
    Integer queryJobGroupId();
}
