package com.crow.web;

import com.crow.domain.*;
import com.crow.utils.DateUtil;
import com.crow.utils.MyUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
public class HandleDataController {

    @Autowired
    FootOddsMapper footOddsMapper;

    @Autowired
    FootDataMapper footDataMapper;

    @Autowired
    FootResultMapper footResultMapper;

    private static BigDecimal dBigHaHa = new BigDecimal(20);

    @GetMapping("/myFootHand")
    public String index1(@Param(value = "size") int size, @Param(value = "week") String week) {

        List<String> needHandle = DateUtil.getAllWant(week, size);
        StringBuffer stringBuffer = new StringBuffer();
        for (String one: needHandle) {
            List<FootOdds> footOddsList = footOddsMapper.list(one, DateUtil.getOneTime(), size, 1);
            StringBuffer winStr = new StringBuffer();
            StringBuffer flatStr = new StringBuffer();
            StringBuffer lossStr = new StringBuffer();
            if (!footOddsList.isEmpty()) {
                for (int i = footOddsList.size() - 1; i > 0; i--) {
                    FootOdds oneF = footOddsList.get(i);
                    FootOdds nextF = footOddsList.get(i - 1);
                    winStr = winStr.append(MyUtil.handle(oneF.getWin(), nextF.getWin()) + ",");
                    flatStr = flatStr.append(MyUtil.handle(oneF.getFlat(), nextF.getFlat()) + ",");
                    lossStr = lossStr.append(MyUtil.handle(oneF.getLoss(), nextF.getLoss()) + ",");
                }
                FootOdds oneFoot = footOddsList.get(0);
                FootResult footResult = findMyResult(MyUtil.findType(oneFoot.getWin()), oneFoot.getName(), winStr.toString(),
                        flatStr.toString(), lossStr.toString());
                if(footResult!=null){
                    footResult.setName(one);
                    stringBuffer.append(footResult.getName() + "---> info: "+
                            footResult.getInfo() + " Chance:" + footResult.getChance()+"\n");
                    //footResultMapper.insert(footResult);
                }else{
                    stringBuffer.append(one + "---> 无相应分析数据\n");
                }

            }
        }
        return stringBuffer.toString();
    }


    private FootResult findMyResult(String type, String footName, String win, String flat, String loss){
        List<FootData> footDataList = footDataMapper.list(type, footName);
        if(footDataList.isEmpty()){
            return null;
        }
        BigDecimal winSame = new BigDecimal(0);
        BigDecimal flatSame = new BigDecimal(0);
        BigDecimal lossSame = new BigDecimal(0);
        for (FootData footData: footDataList){
            BigDecimal isWin = same(footData.getWinData(), win);
            BigDecimal isFalt = same(footData.getFlatData(), flat);
            BigDecimal isLoss = same(footData.getLossData(), loss);
            if(footData.getResult().equals("3")){
                winSame = winSame.add(isWin.add(isFalt).add(isLoss).divide(new BigDecimal(3))
                        .setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if(footData.getResult().equals("1")){
                flatSame = flatSame.add(isWin.add(isFalt).add(isLoss).divide(new BigDecimal(3))
                        .setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if(footData.getResult().equals("0")){
                lossSame = lossSame.add(isWin.add(isFalt).add(isLoss).divide(new BigDecimal(3))
                        .setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        BigDecimal sizeBig = new BigDecimal(footDataList.size())
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        String info = "win:"+winSame.divide(sizeBig).setScale(2, BigDecimal.ROUND_HALF_UP)
                +" flat:"+flatSame.divide(sizeBig).setScale(2, BigDecimal.ROUND_HALF_UP)
                +" loss:"+lossSame.divide(sizeBig).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal mySame = flatSame;
        String sameMsg = "平：";
        if (winSame.compareTo(mySame)>=0){
            mySame = winSame;
            sameMsg = "胜：";
        }
        if(lossSame.compareTo(mySame)>=0){
            mySame = lossSame;
            sameMsg = "负：";
        }
        FootResult footResult = new FootResult();
        footResult.setInfo(info);
        footResult.setCreateTime(new Date());
        footResult.setFootName(footName);
        footResult.setType(type);
        footResult.setChance(sameMsg + mySame.divide((winSame.add(flatSame).add(lossSame)))
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        return footResult;
    }


    private BigDecimal same(String one, String nextOne){
        String[] oneStrs = one.split(",");
        String[] nextOneStrs = nextOne.split(",");
        int size = oneStrs.length;
        double allBili = 0;
        if(oneStrs.length>=nextOneStrs.length){
            size = nextOneStrs.length;
        }
        for (int i=0; i<size; i++){
            BigDecimal oneBig = new BigDecimal(oneStrs[i]);
            BigDecimal nextOneBig = new BigDecimal(nextOneStrs[i]);
            if(oneBig.compareTo(BigDecimal.ZERO)==nextOneBig.compareTo(BigDecimal.ZERO)){
                allBili = allBili + 0.5;
            }
            allBili = allBili + (dBigHaHa.subtract((oneBig.subtract(nextOneBig)))).divide(dBigHaHa)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return new BigDecimal(allBili).divide(new BigDecimal(size));
    }
}
