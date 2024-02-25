package com.sadalsuud.push.domain.channel.repository;

import com.sadalsuud.push.domain.channel.ChannelAccount;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 31/12/2023
 * @Project DoPush-Server
 */
public interface IChannelAccountRepository {
    ChannelAccount save(ChannelAccount channelAccount);

    List<ChannelAccount> save(List<ChannelAccount> channelAccounts);
}
