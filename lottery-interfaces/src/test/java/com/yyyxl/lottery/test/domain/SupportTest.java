package com.yyyxl.lottery.test.domain;


import com.yyyxl.lottery.common.Constants;
import com.yyyxl.lottery.domain.support.ids.IdContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SupportTest {

    private Logger logger = LoggerFactory.getLogger(SupportTest.class);


    @Resource
    private IdContext idContext;

    @Test
    public void test_ids(){
        logger.info("雪花算法策略，生成ID：{}",idContext.iIdGenerator(Constants.Ids.SnowFlake).nextId());
        logger.info("日期算法策略，生成ID：{}",idContext.iIdGenerator(Constants.Ids.ShortCode).nextId());
        logger.info("随机算法策略，生成ID：{}",idContext.iIdGenerator(Constants.Ids.RandomNumeric).nextId());
    }

}
