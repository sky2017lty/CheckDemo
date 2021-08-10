package com.poshing.checkdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (Checklog)实体类
 *
 * @author makejava
 * @since 2021-08-08 16:46:17
 */
public class Checklog extends Model<Checklog> {

    @TableId
    private String uuid;
    
    private String feedingDate;
    
    private String feedingTime;
    
    private String feedingMes;
    
    private String feedingNo;
    
    private String feedingMachine;
    
    private String feedingUsername;
    
    private String cuttingDate;
    
    private String cuttingTime;
    
    private String cuttingMes;
    
    private String cuttingNo;
    
    private String cuttingMachine;
    
    private String cuttingUsername;
    
    private String result;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFeedingDate() {
        return feedingDate;
    }

    public void setFeedingDate(String feedingDate) {
        this.feedingDate = feedingDate;
    }

    public String getFeedingTime() {
        return feedingTime;
    }

    public void setFeedingTime(String feedingTime) {
        this.feedingTime = feedingTime;
    }

    public String getFeedingMes() {
        return feedingMes;
    }

    public void setFeedingMes(String feedingMes) {
        this.feedingMes = feedingMes;
    }

    public String getFeedingNo() {
        return feedingNo;
    }

    public void setFeedingNo(String feedingNo) {
        this.feedingNo = feedingNo;
    }

    public String getFeedingMachine() {
        return feedingMachine;
    }

    public void setFeedingMachine(String feedingMachine) {
        this.feedingMachine = feedingMachine;
    }

    public String getFeedingUsername() {
        return feedingUsername;
    }

    public void setFeedingUsername(String feedingUsername) {
        this.feedingUsername = feedingUsername;
    }

    public String getCuttingDate() {
        return cuttingDate;
    }

    public void setCuttingDate(String cuttingDate) {
        this.cuttingDate = cuttingDate;
    }

    public String getCuttingTime() {
        return cuttingTime;
    }

    public void setCuttingTime(String cuttingTime) {
        this.cuttingTime = cuttingTime;
    }

    public String getCuttingMes() {
        return cuttingMes;
    }

    public void setCuttingMes(String cuttingMes) {
        this.cuttingMes = cuttingMes;
    }

    public String getCuttingNo() {
        return cuttingNo;
    }

    public void setCuttingNo(String cuttingNo) {
        this.cuttingNo = cuttingNo;
    }

    public String getCuttingMachine() {
        return cuttingMachine;
    }

    public void setCuttingMachine(String cuttingMachine) {
        this.cuttingMachine = cuttingMachine;
    }

    public String getCuttingUsername() {
        return cuttingUsername;
    }

    public void setCuttingUsername(String cuttingUsername) {
        this.cuttingUsername = cuttingUsername;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}