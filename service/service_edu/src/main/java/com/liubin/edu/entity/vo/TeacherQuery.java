package com.liubin.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by liubin on 2020/7/6
 */
@Data
public class TeacherQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称,模糊查询")
    private String name;
    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;
    @ApiModelProperty(value = "查询开始时间", example = "2020-01-01 10:10:10")
    private String begin;
    @ApiModelProperty(value = "查询结束时间")
    private String end;
}
