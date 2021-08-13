package com.poshing.checkdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (Feedinggroup)实体类
 *
 * @author makejava
 * @since 2021-08-13 11:06:17
 */
public class Feedinggroup extends Model<Feedinggroup> {

    @TableId
    private String uuid;
    
    private String groupNo;
    
    private String groupTimestamp;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getGroupTimestamp() {
        return groupTimestamp;
    }

    public void setGroupTimestamp(String groupTimestamp) {
        this.groupTimestamp = groupTimestamp;
    }

}