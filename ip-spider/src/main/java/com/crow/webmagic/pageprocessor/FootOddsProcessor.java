package com.crow.webmagic.pageprocessor;

import com.crow.domain.FootOdds;
import com.crow.utils.DateUtil;
import com.crow.utils.UserAgentUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by CrowHawk on 17/10/16.
 */
public class FootOddsProcessor implements PageProcessor {

    private Site site = Site.me().setTimeOut(6000).setRetryTimes(3)
            .setSleepTime(1000)
            .setCharset("GBK")
            .addHeader("Accept-Encoding", "/")
            .setUserAgent(UserAgentUtil.getRandomUserAgent());


    @Override
    public void process(Page page) {
        List<String> ipList = page.getHtml().xpath("//table[@class='zs_tablelist']/tbody[@id='main-tbody']/tr").all();
        List<FootOdds> result = new ArrayList<>();

        if(ipList != null && ipList.size() > 0){
            for(String tmp : ipList){
                Html html = Html.create(tmp);
                FootOdds footOdds = new FootOdds();
                String[] data = html.xpath("//body/text()").toString().trim().split("\\s+");
                if(data==null||data.length<=9){
                    continue;
                }
                List<String> aText = html.xpath("//a/text()").all();
                if(aText.isEmpty()||aText.size()<3){
                    continue;
                }
                footOdds.setScreen(html.xpath("//label/text()").toString());
                footOdds.setName(aText.get(0));
                footOdds.setMatchTime(DateUtil.getDateFromString(data[1]+" "+data[2]));
                footOdds.setVs(aText.get(1)+"VS"+aText.get(2));
                footOdds.setWin(data[7]);
                footOdds.setFlat(data[8]);
                footOdds.setLoss(data[9]);
                footOdds.setCreateTime(new Date());
                result.add(footOdds);
            }
        }
        page.putField("result", result);
        page.addTargetRequest("http://www.xicidaili.com/nn/2");
        page.addTargetRequest("http://www.xicidaili.com/nt/");
    }

    @Override
    public Site getSite() {
        return site;
    }
    /**
     * footOdds.setScreen(data[0]);
     footOdds.setName(data[1]);
     footOdds.setTime(data[3]);
     footOdds.setVs(data[4]+data[5]+data[6]);
     footOdds.setWin(data[11]);
     footOdds.setFlat(data[12]);
     footOdds.setLoss(data[13]);
     footOdds.setCreate(new Date());*/
}
