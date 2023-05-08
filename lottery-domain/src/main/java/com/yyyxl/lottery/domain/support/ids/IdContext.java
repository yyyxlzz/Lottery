package com.yyyxl.lottery.domain.support.ids;

import com.yyyxl.lottery.common.Constants;
import com.yyyxl.lottery.domain.support.ids.policy.RandomNumeric;
import com.yyyxl.lottery.domain.support.ids.policy.ShortCode;
import com.yyyxl.lottery.domain.support.ids.policy.SnowFlake;
import jdk.nashorn.internal.objects.annotations.ScriptClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Id 策略模式上下文配置「在正式的完整的系统架构中，ID 的生成会有单独的服务来完成，其他服务来调用 ID 生成接口即可」
 */
// @Configuration
@Service
public class IdContext {

    /*@Bean
    public Map<Constants.Ids,IIdGenerator> idGenerator(SnowFlake snowFlake, ShortCode shortCode, RandomNumeric randomNumeric) {
        Map<Constants.Ids,IIdGenerator> idGeneratorMap = new HashMap<>(8);
        idGeneratorMap.put(Constants.Ids.SnowFlake,snowFlake);
        idGeneratorMap.put(Constants.Ids.ShortCode,shortCode);
        idGeneratorMap.put(Constants.Ids.RandomNumeric,randomNumeric);

        return idGeneratorMap;
    }*/

    @Resource
    private RandomNumeric randomNumeric;

    @Resource
    private ShortCode shortCode;

    @Resource
    private SnowFlake snowflake;

    Map<Constants.Ids,IIdGenerator> idGeneratorMap = new HashMap<>();

    @PostConstruct
    public void init(){
        idGeneratorMap.put(Constants.Ids.SnowFlake,snowflake);
        idGeneratorMap.put(Constants.Ids.ShortCode,shortCode);
        idGeneratorMap.put(Constants.Ids.RandomNumeric,randomNumeric);
    }

    public IIdGenerator iIdGenerator(Constants.Ids type){
        return idGeneratorMap.get(type);
    }

}
