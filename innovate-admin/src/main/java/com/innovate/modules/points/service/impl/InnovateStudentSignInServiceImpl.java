package com.innovate.modules.points.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.innovate.common.utils.R;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import com.innovate.common.utils.PageUtils;
import com.innovate.common.utils.Query;

import com.innovate.modules.points.dao.InnovateStudentSignInDao;
import com.innovate.modules.points.entity.InnovateStudentSignInEntity;
import com.innovate.modules.points.service.InnovateStudentSignInService;


@Service("innovateStudentSignInService")
public class InnovateStudentSignInServiceImpl extends ServiceImpl<InnovateStudentSignInDao, InnovateStudentSignInEntity> implements InnovateStudentSignInService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InnovateStudentSignInEntity> page = this.selectPage(
                new Query<InnovateStudentSignInEntity>(params).getPage(),
                new EntityWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public R saveAndCheck(InnovateStudentSignInEntity innovateStudentSignIn) {

        //判断是否已经签到

        EntityWrapper<InnovateStudentSignInEntity> entityWrapper = new EntityWrapper<>();

        entityWrapper.eq("activity_id",innovateStudentSignIn.getActivityId());
        entityWrapper.eq("student_id",innovateStudentSignIn.getStudentId());

        InnovateStudentSignInEntity innovateStudentSignInEntity = this.selectOne(entityWrapper);

        if (innovateStudentSignIn==null){
            innovateStudentSignIn.setSignInTime(new Timestamp(System.currentTimeMillis()));
            insert(innovateStudentSignIn);
            return R.ok();
        }else return R.error("你已经签到过了");
    }

    @Override
    public boolean insert(InnovateStudentSignInEntity entity) {
        return super.insert(entity);
    }
}
