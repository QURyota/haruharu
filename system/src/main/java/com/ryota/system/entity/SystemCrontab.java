package com.ryota.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 定时任务执行周期
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SystemCrontab对象", description="定时任务执行周期")
public class SystemCrontab implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "执行周期")
    @TableField("CRONTAB")
    private String crontab;

    @ApiModelProperty(value = "描述")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_USER")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("UPDATE_USER")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATE_TIME",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;


}
