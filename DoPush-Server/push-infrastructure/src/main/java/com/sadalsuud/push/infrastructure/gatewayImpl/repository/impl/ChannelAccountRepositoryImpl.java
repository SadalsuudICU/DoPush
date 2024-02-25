package com.sadalsuud.push.infrastructure.gatewayImpl.repository.impl;

import com.sadalsuud.push.domain.channel.repository.IChannelAccountRepository;
import com.sadalsuud.push.domain.support.gateway.domain.ChannelAccount;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.ChannelAccountDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.infrastructure.repository.impl
 */
@Repository
@RequiredArgsConstructor
public class ChannelAccountRepositoryImpl implements IChannelAccountRepository {
    private final ChannelAccountDao channelAccountDao;

    @Override
    public ChannelAccount save(ChannelAccount channelAccount) {
        return channelAccountDao.save(channelAccount);
    }

    @Override
    public List<ChannelAccount> save(List<ChannelAccount> channelAccounts) {
        return channelAccountDao.saveAll(channelAccounts);
    }
}
