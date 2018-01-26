package com.crow.tasks;


import com.crow.domain.*;
import com.crow.utils.DateUtil;
import com.crow.utils.MyUtil;
import com.crow.webmagic.pageprocessor.FootOddsProcessor;
import com.crow.webmagic.pipeline.FootOddsPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
public class FootOddsTask {
    private static final Logger logger = LoggerFactory.getLogger(FootOddsTask.class);

    @Autowired
    FootOddsPipeline footOddsPipeline;

    @Autowired
    FootOddsMapper footOddsMapper;

    @Autowired
    FootDataMapper footDataMapper;

    private static int size = 10;




    /**
     * 记录盘口数据*/
    @Scheduled(cron="0 55 1,3,5,7,9,11,13,14,15,16,17,19,21,23 * * ?")
    public void executeRecordData() {
        Spider.create(new FootOddsProcessor())
                .addUrl("http://odds.500.com/")
                //.addUrl("http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html")
                .addPipeline(footOddsPipeline)
                .thread(4)
                .run();
        logger.info("FootOddsTask.executeRecordData 数据采集");
    }

    /**
     * 统计完场曲线*/
    @Scheduled(cron="0 35 12 * * ?")
    public void executeHandleData() {
        Date day = new Date();
        Map<String, Date> dateMap = DateUtil.getNearOneDay();
        List<String> needHandle = footOddsMapper.needHandle(dateMap.get("start"), dateMap.get("end"));
        for (String one: needHandle){
            List<FootOdds> footOddsList = footOddsMapper.list(one, DateUtil.getOneTime(), size, 1);
            StringBuffer winStr = new StringBuffer();
            StringBuffer flatStr = new StringBuffer();
            StringBuffer lossStr = new StringBuffer();
            if(!footOddsList.isEmpty()){
                for (int i = footOddsList.size()-1; i>0; i--){
                    FootOdds oneF = footOddsList.get(i);
                    FootOdds nextF = footOddsList.get(i-1);
                    winStr =winStr.append(MyUtil.handle(oneF.getWin(), nextF.getWin())+",");
                    flatStr =flatStr.append(MyUtil.handle(oneF.getFlat(), nextF.getFlat())+",");
                    lossStr =lossStr.append(MyUtil.handle(oneF.getLoss(), nextF.getLoss())+",");
                }
                FootData footData = new FootData();
                footData.setCreateTime(day);
                footData.setFlatData(flatStr.deleteCharAt(flatStr.length() - 1).toString());
                footData.setLossData(lossStr.deleteCharAt(lossStr.length() - 1).toString());
                footData.setName(one);
                footData.setWinData(winStr.deleteCharAt(winStr.length() - 1).toString());
                footData.setType(MyUtil.findType(footOddsList.get(0).getWin()));
                footData.setFootName(footOddsList.get(0).getName());
                footDataMapper.insert(footData);
            }

        }
        logger.info("FootOddsTask.executeHandleData 处理数据");
    }





}
