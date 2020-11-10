package com.innovate.modules.points.service;

import com.baomidou.mybatisplus.service.IService;
import com.innovate.common.utils.PageUtils;
import com.innovate.modules.points.entity.InnovateStudentActivityEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-11-10 13:11:17
 */
public interface InnovateStudentActivityService extends IService<InnovateStudentActivityEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

