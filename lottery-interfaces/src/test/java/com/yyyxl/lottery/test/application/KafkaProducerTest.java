package com.yyyxl.lottery.test.application;

import com.yyyxl.lottery.application.mq.KafkaConsumer;
import com.yyyxl.lottery.application.mq.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description: Kafka 消息测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerTest {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Resource
    private KafkaProducer producer;

    @Test
    public void test_send() throws InterruptedException {
        // 循环发送消息
        while (true) {
            producer.send("你好，我是Lottery 001");
            Thread.sleep(3500);
        }
    }


}
