package com.sadalsuud.push.infrastructure.repository.impl;

import com.sadalsuud.push.domain.gateway.IRepository.ISmsRepository;
import com.sadalsuud.push.infrastructure.repository.SmsRecordDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

}
