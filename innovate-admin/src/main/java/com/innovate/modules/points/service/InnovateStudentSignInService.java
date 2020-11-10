package com.innovate.modules.points.service;

import com.baomidou.mybatisplus.service.IService;
import com.innovate.common.utils.PageUtils;
import com.innovate.common.utils.R;
import com.innovate.modules.points.entity.InnovateStudentSignInEntity;

import java.util.Map;

/**
 * 签到
 *
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-11-10 13:33:51
 */
public interface InnovateStudentSignInService extends IService<InnovateStudentSignInEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R saveAndCheck(InnovateStudentSignInEntity innovateStudentSignIn);
}

