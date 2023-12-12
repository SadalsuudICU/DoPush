package com.sadalsuud.push.client.api;

import com.sadalsuud.push.domain.gateway.domain.ChannelAccount;

import java.util.List;

/**
 * @Description 渠道账号接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.common.client.api
 */
public interface ChannelAccountService {


    /**
     * 保存/修改渠道账号信息
     *
     * @param channelAccount
     * @return
     */
    ChannelAccount save(ChannelAccount channelAccount);

    /**
     * 根据渠道标识查询账号信息
     *
     * @param channelType 渠道标识
     * @param creator     创建者
     * @return
     */
    List<ChannelAccount> queryByChannelType(Integer channelType, String creator);


    /**
     * 列表信息
     *
     * @param creator
     * @return
     */
    List<ChannelAccount> list(String creator);

    /**
     * 软删除(deleted=1)
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

}
