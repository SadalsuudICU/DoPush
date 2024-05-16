package com.sadalsuud.push.application.service;

import com.sadalsuud.push.client.api.ChannelAccountService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.sadalsuud.push.common.constant.CommonConstant;
import com.sadalsuud.push.common.constant.DoPushConstant;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.channel.ChannelAccount;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.ChannelAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.application.service
 */
@Service
public class ChannelAccountServiceImpl implements ChannelAccountService {

    @Autowired
    private ChannelAccountDao channelAccountDao;

    @Override
    public ChannelAccount save(ChannelAccount channelAccount) {
        if (Objects.isNull(channelAccount.getId())) {
            channelAccount.setCreated(Math.toIntExact(DateUtil.currentSeconds()));
            channelAccount.setIsDeleted(CommonConstant.FALSE);
        }
        channelAccount.setCreator(CharSequenceUtil.isBlank(channelAccount.getCreator()) ? DoPushConstant.DEFAULT_CREATOR : channelAccount.getCreator());
        channelAccount.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        return channelAccountDao.save(channelAccount);
    }

    @Override
    public List<ChannelAccount> queryByChannelType(Integer channelType, String creator) {
        return channelAccountDao.findAllByIsDeletedEqualsAndCreatorEqualsAndSendChannelEquals(CommonConstant.FALSE, creator, channelType);
    }

    @Override
    public List<ChannelAccount> list(String creator) {
        return channelAccountDao.findAllByCreatorEquals(creator);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        channelAccountDao.deleteAllById(ids);
    }

    @Override
    public BasicResultVO visualization() {
        List<ChannelAccount> accounts = channelAccountDao.findAll();
        Map<Integer, Long> collect =
                accounts.stream().collect(Collectors.groupingBy(ChannelAccount::getSendChannel, Collectors.counting()));

        HashMap<String, Long> data = new HashMap<>();
        for (ChannelType type : ChannelType.values()) {
            Integer code = type.getCode();
            String des = type.getDescription();
            Long l = collect.get(code);
            l = l == null ? 0 : l;
            data.put(des, l);
        }

        return BasicResultVO.success(data);
    }
}
