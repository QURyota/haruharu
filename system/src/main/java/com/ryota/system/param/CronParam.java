package com.ryota.system.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ryota.system.entity.SystemJobParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Ryota
 * @create 2022/6/4 10:58
 */
@Data
public class CronParam {

    private String id;

    @ApiModelProperty(value = "cron表达式")
    private String cron;
    @ApiModelProperty(value = "cron描述")
    private String cronDesc;
    @ApiModelProperty(value = "任务接口url")
    private String interfaceUrl;
    @ApiModelProperty(value = "任务接口url描述")
    private String interfaceUrlDesc;
    @ApiModelProperty(value = "定时任务描述")
    private String description;

    @ApiModelProperty(value = "重试次数")
    private Integer retryNum;

    @ApiModelProperty(value = "系统编码")
    private String systemCode;

    private List<SystemJobParam> params;

}
