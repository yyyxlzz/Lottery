package com.yyyxl.lottery.infrastructure.repository;


import com.yyyxl.lottery.domain.award.repository.IOrderRepository;
import com.yyyxl.lottery.infrastructure.dao.IUserStrategyExportDao;
import com.yyyxl.lottery.infrastructure.po.UserStrategyExport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @description: 奖品表仓储服务
 * @date: 2023/5/5
 */
@Repository
public class OrderRepository implements IOrderRepository {
    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setAwardId(awardId);
        userStrategyExport.setGrantState(grantState);
        userStrategyExportDao.updateUserAwardState(userStrategyExport);
    }

}
