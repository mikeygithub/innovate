package com.innovate.modules.points.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovate.modules.points.entity.InnovateStudentSignInEntity;
import com.innovate.modules.points.service.InnovateStudentSignInService;
import com.innovate.common.utils.PageUtils;
import com.innovate.common.utils.R;



/**
 * 签到
 *
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-11-10 13:33:51
 */
@RestController
@RequestMapping("points/innovatestudentsignin")
public class InnovateStudentSignInController {
    @Autowired
    private InnovateStudentSignInService innovateStudentSignInService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("points:innovatestudentsignin:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = innovateStudentSignInService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("points:innovatestudentsignin:info")
    public R info(@PathVariable("id") Long id){
		InnovateStudentSignInEntity innovateStudentSignIn = innovateStudentSignInService.selectById(id);

        return R.ok().put("innovateStudentSignIn", innovateStudentSignIn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("points:innovatestudentsignin:save")
    public R save(@RequestBody InnovateStudentSignInEntity innovateStudentSignIn){

        R r = innovateStudentSignInService.saveAndCheck(innovateStudentSignIn);

        return r;
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("points:innovatestudentsignin:update")
    public R update(@RequestBody InnovateStudentSignInEntity innovateStudentSignIn){
		innovateStudentSignInService.updateById(innovateStudentSignIn);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("points:innovatestudentsignin:delete")
    public R delete(@RequestBody Long[] ids){
		innovateStudentSignInService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
