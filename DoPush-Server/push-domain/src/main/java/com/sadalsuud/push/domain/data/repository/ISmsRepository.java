package com.sadalsuud.push.domain.data.repository;

import com.sadalsuud.push.domain.support.gateway.domain.SmsRecord;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 31/12/2023
 * @Project DoPush-Server
 */
public interface ISmsRepository {
    List<SmsRecord> saveAll(List<SmsRecord> records);

    List<SmsRecord> findByPhoneAndSendDate(Long aLong, Integer sendDate);
}
