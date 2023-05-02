package com.yyyxl.lottery.domain.strategy.service.draw;

import com.yyyxl.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DrawConfig {

    @Resource
    private IDrawAlgorithm defaultRateRandomDrawAlgorithm;
    @Resource
    private IDrawAlgorithm singleRateRandomDrawAlgorithm;

    // 考虑到运营可能配置的活动较多在大促期间，避免因为以后优化为多线程方式带来问题，所以处理为 ConcurrentHashMap 不过暂时这里使用 HashMap 也行
    protected static Map<Integer, IDrawAlgorithm> drawAlgorithmMap = new ConcurrentHashMap<>();

    //  使用@PostConstruct注解修饰的init方法就会在Spring容器的启动时自动的执行
    @PostConstruct
    public void init() {
        drawAlgorithmMap.put(1,defaultRateRandomDrawAlgorithm);
        drawAlgorithmMap.put(2,singleRateRandomDrawAlgorithm);
    }

}
