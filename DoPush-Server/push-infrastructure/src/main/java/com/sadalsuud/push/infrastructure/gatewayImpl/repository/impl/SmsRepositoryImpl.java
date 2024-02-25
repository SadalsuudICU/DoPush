package com.sadalsuud.push.infrastructure.gatewayImpl.repository.impl;

import com.google.common.collect.Lists;
import com.sadalsuud.push.domain.data.repository.ISmsRepository;
import com.sadalsuud.push.domain.data.SmsRecord;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.SmsRecordDao;
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
public class SmsRepositoryImpl implements ISmsRepository {
    private final SmsRecordDao smsRecordDao;

    @Override
    public List<SmsRecord> saveAll(List<SmsRecord> records) {
        return Lists.newArrayList(smsRecordDao.saveAll(records));
    }

    @Override
    public List<SmsRecord> findByPhoneAndSendDate(Long aLong, Integer sendDate) {
        return smsRecordDao.findByPhoneAndSendDate(aLong, sendDate);
    }
}
