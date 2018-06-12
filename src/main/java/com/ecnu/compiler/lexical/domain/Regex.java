package com.ecnu.compiler.lexical.domain;

import com.baomidou.mybatisplus.annotations.TableName;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Michael Chen
 */
@TableName("regex")
public class Regex extends Model<Regex>{

    private Integer id;
    private String name;
    private String regex;
    private Integer priority;
    private Integer type;
    private Integer compilerId;
    private Timestamp gmtCreated;
    private Timestamp gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCompilerId() {
        return compilerId;
    }

    public void setCompilerId(Integer compilerId) {
        this.compilerId = compilerId;
    }

    public Timestamp getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Timestamp gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
