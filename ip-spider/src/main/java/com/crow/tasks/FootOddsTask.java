package com.crow.tasks;


import com.crow.domain.FootData;
import com.crow.domain.FootDataMapper;
import com.crow.domain.FootOdds;
import com.crow.domain.FootOddsMapper;
import com.crow.utils.DateUtil;
import com.crow.webmagic.pageprocessor.FootOddsProcessor;
import com.crow.webmagic.pipeline.FootOddsPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.math.BigDecimal;
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
                    winStr =winStr.append(handle(oneF.getWin(), nextF.getWin())+",");
                    flatStr =flatStr.append(handle(oneF.getFlat(), nextF.getFlat())+",");
                    lossStr =lossStr.append(handle(oneF.getLoss(), nextF.getLoss())+",");
                }
                FootData footData = new FootData();
                footData.setCreateTime(day);
                footData.setFlatData(flatStr.deleteCharAt(flatStr.length() - 1).toString());
                footData.setLossData(lossStr.deleteCharAt(lossStr.length() - 1).toString());
                footData.setName(one);
                footData.setWinData(winStr.deleteCharAt(winStr.length() - 1).toString());
                footData.setType(findType(footOddsList.get(0).getWin()));
                footDataMapper.insert(footData);
            }

        }

        logger.info("FootOddsTask.executeHandleData 处理数据");
    }

    private String handle(String one, String nextOne){
        BigDecimal oneBig = new BigDecimal(one);
        BigDecimal nextOneBig = new BigDecimal(nextOne);
        int data = nextOneBig.subtract(oneBig).multiply(new BigDecimal(100)).intValue();
        data = data/2;
        if(data<-9){
            data = -9;
        }else if(data>9){
            data = 9;
        }
        return String.valueOf(data);
    }

    private String findType(String one){
        Double oneDouble = Double.parseDouble(one);
        String out = "-1";
        if(oneDouble<=1.10){
            out = "0";
        }if(oneDouble>1.10&&oneDouble<=1.30){
            out = "1";
        }else if(oneDouble>1.30&&oneDouble<=1.60){
            out = "2";
        }else if(oneDouble>1.60&&oneDouble<=1.85){
            out = "3";
        }else if(oneDouble>1.85&&oneDouble<=2.15){
            out = "4";
        }else if(oneDouble>2.15&&oneDouble<=2.45){
            out = "5";
        }else if(oneDouble>2.45&&oneDouble<=2.85){
            out = "6";
        }else if(oneDouble>2.85&&oneDouble<=3.25){
            out = "7";
        }else if(oneDouble>3.28&&oneDouble<=4){
            out = "8";
        }else if(oneDouble>4&&oneDouble<=6){
            out = "9";
        }else if(oneDouble>6){
            out = "10";
        }
        return out;
    }
}
