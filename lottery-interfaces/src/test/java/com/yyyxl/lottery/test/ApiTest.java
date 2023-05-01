package com.yyyxl.lottery.test;


import com.yyyxl.lottery.infrastructure.dao.IActivityDao;
import com.yyyxl.lottery.infrastructure.po.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import com.alibaba.fastjson.JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {
    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Resource
    private IActivityDao activityDao;

    @Test
    public void test_insert(){
        Activity activity = new Activity();
        activity.setActivityId(100002L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("仅用于插入数据测试");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("yyyxl");
        activityDao.insert(activity);
    }

    @Test
    public void test_query(){
        Activity activity = activityDao.queryActivityById(100002L);
        logger.info("查询数据：{}",JSON.toJSONString(activity));
    }

}
