package com.crow.webmagic.pipeline;

import com.crow.domain.FootOdds;
import com.crow.domain.FootOddsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

@Component("footOddsPipeline")
public class FootOddsPipeline implements Pipeline{

    @Autowired
    FootOddsMapper footOddsMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().equals("result")) {
                List<FootOdds> footOdds = (List<FootOdds>) entry.getValue();
                for(FootOdds footOdd: footOdds) {
                    footOddsMapper.insert(footOdd);
                }
            }
        }
    }

}
