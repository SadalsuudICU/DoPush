package com.sadalsuud.push.domain.assign.deduplication.limit;


import com.sadalsuud.push.common.domain.TaskInfo;
import com.sadalsuud.push.domain.assign.deduplication.DeduplicationParam;
import com.sadalsuud.push.domain.assign.deduplication.service.AbstractDeduplicationService;

import java.util.Set;

/**
 * @author cao
 * @date 2022-04-20 11:58
 */
public interface LimitService {


    /**
     * 去重限制
     *
     * @param service  去重器对象
     * @param taskInfo
     * @param param    去重参数
     * @return 返回不符合条件的手机号码
     */
    Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param);

}
