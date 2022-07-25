package com.ryota.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author Ryota
 * @create 2022/6/5 21:18
 */
@Data
public class SystemJobCronVO {

    private String id;
    private String cron;
    private String cronDesc;
    private String description;
    private Boolean status;
    private String interfaceUrl;
    private String interfaceUrlDesc;
    private String systemCode;
    private String createUser;
    private Date createTime;
    private Integer retryNum;

}
