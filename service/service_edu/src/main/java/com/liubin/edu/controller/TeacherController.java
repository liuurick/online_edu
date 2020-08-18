package com.liubin.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liubin.commonutils.R;
import com.liubin.edu.entity.Teacher;
import com.liubin.edu.entity.vo.TeacherQuery;
import com.liubin.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author liubin
 * @since 2020-06-10
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    //1 查询讲师表所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        return flag ? R.ok():R.error();
    }

    /**
     * 分页查询
     * current 当前页
     * limit 每页记录数
     */
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current,@PathVariable Long limit){
        //创建page对象
        Page<Teacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法的时候，底层封装，会把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);
        List<Teacher> records = pageTeacher.getRecords();
        long total = pageTeacher.getTotal();

        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 条件查询带分页
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false)TeacherQuery teacherQuery){
        //创建page对象
        Page<Teacher> pageTeacher = new Page<>(current,limit);
        //构造条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        teacherService.page(pageTeacher,wrapper);
        Long total = pageTeacher.getTotal();
        List<Teacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 讲师添加
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R save(@ApiParam(name = "teacher", value = "讲师对象", required = true)
        @RequestBody Teacher teacher){

        boolean flag = teacherService.save(teacher);
        return flag?R.ok():R.error();
    }

    /**
     * 根据id查询讲师
     */
    @ApiOperation(value = "根据id查询教师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    /**
     * 修改教师
     */
    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("updateTeacher")
    public R updateById(@RequestBody Teacher teacher){
        boolean flag = teacherService.updateById(teacher);
        return flag?R.ok().data("teacher",teacher):R.error().data("teacher",teacher);
    }
}

