package com.crow.domain;

import java.io.Serializable;
import java.util.Date;

public class FootData implements Serializable {

    private Long id;

    private String name;

    private String winData;

    private String flatData;

    private String lossData;

    private String result;

    private String type;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWinData() {
        return winData;
    }

    public void setWinData(String winData) {
        this.winData = winData;
    }

    public String getFlatData() {
        return flatData;
    }

    public void setFlatData(String flatData) {
        this.flatData = flatData;
    }

    public String getLossData() {
        return lossData;
    }

    public void setLossData(String lossData) {
        this.lossData = lossData;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
