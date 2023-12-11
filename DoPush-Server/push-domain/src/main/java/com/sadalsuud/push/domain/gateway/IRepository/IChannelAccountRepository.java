package com.sadalsuud.push.domain.gateway.IRepository;

import com.sadalsuud.push.domain.gateway.domain.ChannelAccount;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.gateway.IRepository
 */
public interface IChannelAccountRepository {
    ChannelAccount save(ChannelAccount channelAccount);

    List<ChannelAccount> save(List<ChannelAccount> channelAccounts);
}
