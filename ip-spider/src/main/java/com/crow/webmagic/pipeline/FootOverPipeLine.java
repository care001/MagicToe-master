package com.crow.webmagic.pipeline;

import com.crow.domain.FootData;
import com.crow.domain.FootDataMapper;
import com.crow.domain.FootOdds;
import com.crow.domain.FootOddsMapper;
import com.crow.utils.DateUtil;
import com.crow.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("footOverPipeLine")
public class FootOverPipeLine implements Pipeline {
    @Autowired
    FootDataMapper footDataMapper;
    @Autowired
    FootOddsMapper footOddsMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().equals("result")) {
                Map<String, String> map = (Map<String, String>) entry.getValue();
                Date day = new Date();
                Map<String, Date> dateMap = DateUtil.getNearOneDay();
                List<String> needHandle = footOddsMapper.needHandle(dateMap.get("start"), dateMap.get("end"));
                for (String one: needHandle){
                    List<FootOdds> footOddsList = footOddsMapper.list(one, DateUtil.getOneTime(), 10, 1);
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
                        footData.setResult(map.get(one));
                        footDataMapper.insert(footData);
                    }

                }

            }
        }
    }
}
