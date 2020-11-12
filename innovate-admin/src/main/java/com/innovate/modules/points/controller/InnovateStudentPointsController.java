package com.innovate.modules.points.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.excel.EasyExcel;
import com.innovate.modules.innovate.config.ConfigApi;
import com.innovate.modules.points.utils.InnovateStudentPointsEntityListener;
import com.innovate.modules.util.FileUtils;
import com.innovate.modules.util.RandomUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.innovate.modules.points.entity.InnovateStudentPointsEntity;
import com.innovate.modules.points.service.InnovateStudentPointsService;
import com.innovate.common.utils.PageUtils;
import com.innovate.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-08-09 09:13:27
 */
@RestController
@RequestMapping("points/innovatestudentpoints")
public class InnovateStudentPointsController {
    @Autowired
    private InnovateStudentPointsService innovateStudentPointsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("points:innovatestudentpoints:list")
    public R list(@RequestParam Map<String, Object> params){
        //记录
        PageUtils page = innovateStudentPointsService.queryPage(params);
        //总分
        Integer total = null;
        if (params.get("userId")!=null)total = innovateStudentPointsService.queryPointByParams(params);
        return R.ok().put("page", page).put("total",total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("points:innovatestudentpoints:info")
    public R info(@PathVariable("id") Long id){
		InnovateStudentPointsEntity innovateStudentPoints = innovateStudentPointsService.selectById(id);

        return R.ok().put("innovateStudentPoints", innovateStudentPoints);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("points:innovatestudentpoints:save")
    public R save(@RequestBody InnovateStudentPointsEntity innovateStudentPoints){
        //生成二维码
		innovateStudentPointsService.insert(innovateStudentPoints);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("points:innovatestudentpoints:update")
    public R update(@RequestBody InnovateStudentPointsEntity innovateStudentPoints){
		innovateStudentPointsService.updateById(innovateStudentPoints);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("points:innovatestudentpoints:delete")
    public R delete(@RequestBody Long[] ids){
		innovateStudentPointsService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 文件上传导入分数
     * @param file
     * @return
     */
    @PostMapping(value = "/input")
    @RequiresPermissions("points:innovatestudentpoints:save")
    public Object uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            EasyExcel.read(file.getInputStream(), InnovateStudentPointsEntity.class, new InnovateStudentPointsEntityListener(innovateStudentPointsService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok("导入成功");
    }
}
