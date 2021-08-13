package com.poshing.checkdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (Feedingcheck)实体类
 *
 * @author makejava
 * @since 2021-08-08 16:46:17
 */
public class Feedingcheck extends Model<Feedingcheck> {

    @TableId
    private String uuid;
    
    private String date;
    
    private String time;
    
    private String feedingMes;
    
    private String feedingNo;
    
    private String feedingGroup;

    private String feedingGroupno;

    private String username;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getFeedingGroup() {
        return feedingGroup;
    }

    public void setFeedingGroup(String feedingGroup) {
        this.feedingGroup = feedingGroup;
    }

    public String getFeedingGroupno() {
        return feedingGroupno;
    }

    public void setFeedingGroupno(String feedingGroupno) {
        this.feedingGroupno = feedingGroupno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}