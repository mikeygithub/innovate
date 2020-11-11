package com.innovate.modules.points.service;

import com.baomidou.mybatisplus.service.IService;
import com.innovate.common.utils.PageUtils;
import com.innovate.common.utils.R;
import com.innovate.modules.points.entity.InnovateStudentPointsEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-08-09 09:13:27
 */
public interface InnovateStudentPointsService extends IService<InnovateStudentPointsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer queryPointByParams(Map<String, Object> params);

    R insertAndCheck(InnovateStudentPointsEntity innovateStudentPoints);
}

