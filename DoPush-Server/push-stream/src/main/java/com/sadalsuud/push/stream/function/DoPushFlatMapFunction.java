package com.sadalsuud.push.stream.function;

import com.alibaba.fastjson.JSON;
import com.sadalsuud.push.common.domain.AnchorInfo;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @Description 数据处理
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.stream.function
 */
public class DoPushFlatMapFunction implements FlatMapFunction<String, AnchorInfo> {

    @Override
    public void flatMap(String value, Collector<AnchorInfo> collector) throws Exception {
        AnchorInfo anchorInfo = JSON.parseObject(value, AnchorInfo.class);
        collector.collect(anchorInfo);
    }
}
