package com.sadalsuud.push.stream;

import com.sadalsuud.push.common.domain.AnchorInfo;
import com.sadalsuud.push.stream.constants.FlinkConstant;
import com.sadalsuud.push.stream.function.DoPushFlatMapFunction;
import com.sadalsuud.push.stream.sink.DoPushSink;
import com.sadalsuud.push.stream.utils.MessageQueueUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 17/12/2023
 * @Package com.sadalsuud.push.stream
 */
@Slf4j
public class StreamBootStrap {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        /*
            获取kafka消费信息
         */
        KafkaSource<String> kafkaConsumer =
                MessageQueueUtils.getKafkaConsumer(FlinkConstant.TOPIC_NAME, FlinkConstant.GROUP_ID, FlinkConstant.BROKER);
        DataStreamSource<String> kafkaSource =
                env.fromSource(kafkaConsumer, WatermarkStrategy.noWatermarks(), FlinkConstant.SOURCE_NAME);

        /*
            进行处理 转换数据
         */
        SingleOutputStreamOperator<AnchorInfo> dataStream =
                kafkaSource.flatMap(new DoPushFlatMapFunction()).name(FlinkConstant.FUNCTION_NAME);


        /*
            将实时多维数据写入redis
         */
        dataStream.addSink(new DoPushSink()).name(FlinkConstant.SINK_NAME);
        env.execute(FlinkConstant.JOB_NAME);

    }
}
