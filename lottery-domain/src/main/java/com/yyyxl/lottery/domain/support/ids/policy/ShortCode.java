package com.yyyxl.lottery.domain.support.ids.policy;

import com.yyyxl.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;

@Component
public class ShortCode implements IIdGenerator {
    @Override
    public Long nextId() {

        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int week = instance.get(Calendar.WEEK_OF_YEAR);
        int day = instance.get(Calendar.DAY_OF_WEEK);
        int hour = instance.get(Calendar.HOUR_OF_DAY);

        // 打乱排序
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year-2020);
        stringBuilder.append(hour);
        stringBuilder.append(String.format("%02d",week));
        stringBuilder.append(day);
        stringBuilder.append(String.format("%03d",new Random().nextInt(1000)));
        return Long.parseLong(stringBuilder.toString());
    }
}
