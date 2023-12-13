package com.sadalsuud.push.domain.receipt.deduplication.service;


import com.sadalsuud.push.domain.receipt.deduplication.DeduplicationParam;

/**
 * @author huskey
 * @date 2022/1/18
 */
public interface DeduplicationService {

    /**
     * 去重
     *
     * @param param
     */
    void deduplication(DeduplicationParam param);
}
