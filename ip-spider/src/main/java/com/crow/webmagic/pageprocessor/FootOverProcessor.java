package com.crow.webmagic.pageprocessor;

import com.crow.utils.UserAgentUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.*;

public class FootOverProcessor implements PageProcessor {

    private Site site = Site.me().setTimeOut(6000).setRetryTimes(3)
            .setSleepTime(1000)
            .setCharset("GBK")
            .addHeader("Accept-Encoding", "/")
            .setUserAgent(UserAgentUtil.getRandomUserAgent());


    @Override
    public void process(Page page) {
        List<String> ipList = page.getHtml().xpath("//table[@id='table_match']/tbody/tr").all();
        Map<String, String> map = new HashMap<>();
        if(ipList != null && ipList.size() > 0){
            for(String tmp : ipList){
                Html html = Html.create(tmp);
                String[] data = html.xpath("//body/text()").toString().trim().split("\\s+");
                if(data!=null&&data.length>=8&&(data[7].equals("胜")||data[7].equals("负")||data[7].equals("平"))){
                    map.put(data[0], data[7]);
                }
            }
        }
        page.putField("result", map);
        page.addTargetRequest("http://www.xicidaili.com/nn/2");
        page.addTargetRequest("http://www.xicidaili.com/nt/");
    }

    @Override
    public Site getSite() {
        return site;
    }
}
