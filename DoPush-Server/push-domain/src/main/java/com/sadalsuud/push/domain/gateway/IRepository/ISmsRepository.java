package com.sadalsuud.push.domain.gateway.IRepository;

import com.sadalsuud.push.domain.gateway.domain.SmsRecord;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 * @Package com.sadalsuud.push.domain.gateway.IRepository
 */
public interface ISmsRepository {
    List<SmsRecord> saveAll(List<SmsRecord> records);

    List<SmsRecord> findByPhoneAndSendDate(Long aLong, Integer sendDate);
}
