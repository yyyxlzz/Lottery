package com.itedus.lottery.rpc;

import com.itedus.lottery.rpc.req.ActivityReq;
import com.itedus.lottery.rpc.res.ActivityRes;

public interface IActivityBooth {

    ActivityRes queryActivityById(ActivityReq req);

}
