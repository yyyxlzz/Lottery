package com.yyyxl.lottery.application.process.res;

import com.yyyxl.lottery.common.Result;
import com.yyyxl.lottery.domain.strategy.model.vo.DrawAwardVO;

/**
 * @description: 活动抽奖结果
 */
public class DrawProcessResult extends Result {

    private DrawAwardVO drawAwardVO;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawAwardVO drawAwardVO) {
        super(code, info);
        this.drawAwardVO = drawAwardVO;
    }

    public DrawAwardVO getDrawAwardVO() {
        return drawAwardVO;
    }

    public void setDrawAwardVO(DrawAwardVO drawAwardVO) {
        this.drawAwardVO = drawAwardVO;
    }
}
