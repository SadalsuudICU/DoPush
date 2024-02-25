package com.sadalsuud.push.application.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sadalsuud.push.client.api.MessageTemplateService;
import com.sadalsuud.push.client.dto.MessageTemplateParam;
import com.sadalsuud.push.common.constant.CommonConstant;
import com.sadalsuud.push.common.constant.DoPushConstant;
import com.sadalsuud.push.common.enums.AuditStatus;
import com.sadalsuud.push.common.enums.MessageStatus;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.support.gateway.domain.MessageTemplate;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.MessageTemplateDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description 消息模板管理接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.application.service
 */
@Service
@RequiredArgsConstructor
public class MessageTemplateServiceImpl implements MessageTemplateService {

    private final MessageTemplateDao messageTemplateDao;

    //private CronTaskService cronTaskService;
    //
    //private XxlJobUtils xxlJobUtils;

    @Override
    public Page<MessageTemplate> queryList(MessageTemplateParam param) {
        PageRequest pageRequest = PageRequest.of(param.getPage() - 1, param.getPerPage());
        String creator = CharSequenceUtil.isBlank(param.getCreator()) ? DoPushConstant.DEFAULT_CREATOR : param.getCreator();
        return messageTemplateDao.findAll((Specification<MessageTemplate>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // 加搜索条件
            if (CharSequenceUtil.isNotBlank(param.getKeywords())) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + param.getKeywords() + "%"));
            }
            predicateList.add(cb.equal(root.get("isDeleted").as(Integer.class), CommonConstant.FALSE));
            predicateList.add(cb.equal(root.get("creator").as(String.class), creator));
            Predicate[] p = new Predicate[predicateList.size()];
            // 查询
            query.where(cb.and(predicateList.toArray(p)));
            // 排序
            query.orderBy(cb.desc(root.get("updated")));
            return query.getRestriction();
        }, pageRequest);
    }

    @Override
    public Long count() {
        return messageTemplateDao.countByIsDeletedEquals(CommonConstant.FALSE);
    }

    @Override
    public MessageTemplate saveOrUpdate(MessageTemplate messageTemplate) {
        if (Objects.isNull(messageTemplate.getId())) {
            initStatus(messageTemplate);
        } else {
            resetStatus(messageTemplate);
        }

        messageTemplate.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        return messageTemplateDao.save(messageTemplate);
    }


    @Override
    public void deleteByIds(List<Long> ids) {
        Iterable<MessageTemplate> messageTemplates = messageTemplateDao.findAllById(ids);
        messageTemplates.forEach(messageTemplate -> messageTemplate.setIsDeleted(CommonConstant.TRUE));
        for (MessageTemplate messageTemplate : messageTemplates) {
            if (Objects.nonNull(messageTemplate.getCronTaskId()) && messageTemplate.getCronTaskId() > 0) {
                // TODO 定时任务
                //cronTaskService.deleteCronTask(messageTemplate.getCronTaskId());
            }
        }
        messageTemplateDao.saveAll(messageTemplates);
    }

    @Override
    public MessageTemplate queryById(Long id) {
        return messageTemplateDao.findById(id).orElse(null);
    }

    @Override
    public void copy(Long id) {
        MessageTemplate messageTemplate = messageTemplateDao.findById(id).orElse(null);
        if (Objects.nonNull(messageTemplate)) {
            MessageTemplate clone = ObjectUtil.clone(messageTemplate).setId(null).setCronTaskId(null);
            messageTemplateDao.save(clone);
        }
    }

    @Override
    public BasicResultVO startCronTask(Long id) {
        //// 1.获取消息模板的信息
        //MessageTemplate messageTemplate = messageTemplateDao.findById(id).orElse(null);
        //if (Objects.isNull(messageTemplate)) {
        //    return BasicResultVO.fail();
        //}
        //
        //// 2.动态创建或更新定时任务
        //// TODO
        //XxlJobInfo xxlJobInfo = xxlJobUtils.buildXxlJobInfo(messageTemplate);
        //
        //// 3.获取taskId(如果本身存在则复用原有任务，如果不存在则得到新建后任务ID)
        //Integer taskId = messageTemplate.getCronTaskId();
        //BasicResultVO basicResultVO = cronTaskService.saveCronTask(xxlJobInfo);
        //if (Objects.isNull(taskId) && RespStatusEnum.SUCCESS.getCode().equals(basicResultVO.getStatus()) && Objects.nonNull(basicResultVO.getData())) {
        //    taskId = Integer.valueOf(String.valueOf(basicResultVO.getData()));
        //}
        //
        //// 4. 启动定时任务
        //if (Objects.nonNull(taskId)) {
        //    cronTaskService.startCronTask(taskId);
        //    MessageTemplate clone = ObjectUtil.clone(messageTemplate).setMsgStatus(MessageStatus.RUN.getCode()).setCronTaskId(taskId).setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        //    messageTemplateDao.save(clone);
        //    return BasicResultVO.success();
        //}
        return BasicResultVO.fail();
    }

    @Override
    public BasicResultVO stopCronTask(Long id) {
        //// 1.修改模板状态
        //MessageTemplate messageTemplate = messageTemplateDao.findById(id).orElse(null);
        //if (Objects.isNull(messageTemplate)) {
        //    return BasicResultVO.fail();
        //}
        //MessageTemplate clone = ObjectUtil.clone(messageTemplate).setMsgStatus(MessageStatus.STOP.getCode()).setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        //messageTemplateDao.save(clone);
        //
        //// 2.暂停定时任务
        //return cronTaskService.stopCronTask(clone.getCronTaskId());
        return BasicResultVO.fail();

    }


    /**
     * 初始化状态信息
     *
     * @param messageTemplate
     */
    private void initStatus(MessageTemplate messageTemplate) {
        messageTemplate.setFlowId(CharSequenceUtil.EMPTY)
                .setMsgStatus(MessageStatus.INIT.getCode()).setAuditStatus(AuditStatus.WAIT_AUDIT.getCode())
                .setCreator(CharSequenceUtil.isBlank(messageTemplate.getCreator()) ? DoPushConstant.DEFAULT_CREATOR : messageTemplate.getCreator())
                .setUpdator(CharSequenceUtil.isBlank(messageTemplate.getUpdator()) ? DoPushConstant.DEFAULT_UPDATOR : messageTemplate.getUpdator())
                .setTeam(CharSequenceUtil.isBlank(messageTemplate.getTeam()) ? DoPushConstant.DEFAULT_TEAM : messageTemplate.getTeam())
                .setAuditor(CharSequenceUtil.isBlank(messageTemplate.getAuditor()) ? DoPushConstant.DEFAULT_AUDITOR : messageTemplate.getAuditor())
                .setCreated(Math.toIntExact(DateUtil.currentSeconds()))
                .setIsDeleted(CommonConstant.FALSE);

    }

    /**
     * 1. 重置模板的状态
     * 2. 修改定时任务信息(如果存在)
     *
     * @param messageTemplate
     */
    private void resetStatus(MessageTemplate messageTemplate) {
        messageTemplate.setUpdator(messageTemplate.getUpdator())
                .setMsgStatus(MessageStatus.INIT.getCode()).setAuditStatus(AuditStatus.WAIT_AUDIT.getCode());

        // 从数据库查询并注入 定时任务 ID
        MessageTemplate dbMsg = queryById(messageTemplate.getId());
        if (Objects.nonNull(dbMsg) && Objects.nonNull(dbMsg.getCronTaskId())) {
            messageTemplate.setCronTaskId(dbMsg.getCronTaskId());
        }

        // TODO
        //if (Objects.nonNull(messageTemplate.getCronTaskId()) && TemplateType.CLOCKING.getCode().equals(messageTemplate.getTemplateType())) {
        //    XxlJobInfo xxlJobInfo = xxlJobUtils.buildXxlJobInfo(messageTemplate);
        //    cronTaskService.saveCronTask(xxlJobInfo);
        //    cronTaskService.stopCronTask(messageTemplate.getCronTaskId());
        //}
    }


}
