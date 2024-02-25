package com.sadalsuud.push.infrastructure.gatewayImpl.repository;

import com.sadalsuud.push.domain.support.gateway.domain.ChannelAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description 渠道账号Dao
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.infrastructure.repository
 */
public interface ChannelAccountDao extends JpaRepository<ChannelAccount, Long> {


    /**
     * 查询 列表
     *
     * @param deleted     0：未删除 1：删除
     * @param channelType 渠道值
     * @param creator     创建者
     * @return
     */
    List<ChannelAccount> findAllByIsDeletedEqualsAndCreatorEqualsAndSendChannelEquals(Integer deleted, String creator, Integer channelType);

    /**
     * 查询 列表
     *
     * @param deleted     0：未删除 1：删除
     * @param channelType 渠道值
     * @return
     */
    List<ChannelAccount> findAllByIsDeletedEqualsAndSendChannelEquals(Integer deleted, Integer channelType);

    /**
     * 根据创建者检索相关的记录
     *
     * @param creator
     * @return
     */
    List<ChannelAccount> findAllByCreatorEquals(String creator);

    /**
     * 统计未删除的条数
     *
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);

}
