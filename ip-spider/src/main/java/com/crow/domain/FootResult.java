package com.crow.domain;

import java.io.Serializable;
import java.util.Date;

public class FootResult implements Serializable {
    private Long id;

    private String name;

    private String info;

    private String chance;

    private String result;

    private String type;

    private Date createTime;

    private String footName;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getChance() {
        return chance;
    }

    public void setChance(String chance) {
        this.chance = chance;
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

    public String getFootName() {
        return footName;
    }

    public void setFootName(String footName) {
        this.footName = footName;
    }
}
