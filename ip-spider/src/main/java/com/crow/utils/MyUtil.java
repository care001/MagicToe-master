package com.crow.utils;

import java.math.BigDecimal;

public class MyUtil {

    public static String handle(String one, String nextOne){
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

    public static String findType(String one){
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
