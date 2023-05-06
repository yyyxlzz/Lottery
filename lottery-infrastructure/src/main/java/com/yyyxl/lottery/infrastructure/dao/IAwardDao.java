package com.yyyxl.lottery.infrastructure.dao;

import com.yyyxl.lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 奖品信息表DAO
 */
@Mapper
public interface IAwardDao {

    /**
     * 查询奖品信息
     *
     * @param awardId 奖品ID
     * @return        奖品信息
     */
    Award queryAwardInfo(String awardId);

    /**
     * 插入奖品配置
     * @param list 奖品配置
     * @return
     */
    void insertList(List<Award> list);
}
