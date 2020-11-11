package com.innovate.modules.points.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.innovate.common.utils.R;
import com.innovate.modules.innovate.entity.UserPersonInfoEntity;
import com.innovate.modules.innovate.service.UserPerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.innovate.common.utils.PageUtils;
import com.innovate.common.utils.Query;

import com.innovate.modules.points.dao.InnovateStudentPointsDao;
import com.innovate.modules.points.entity.InnovateStudentPointsEntity;
import com.innovate.modules.points.service.InnovateStudentPointsService;


@Service("innovateStudentPointsService")
public class InnovateStudentPointsServiceImpl extends ServiceImpl<InnovateStudentPointsDao, InnovateStudentPointsEntity> implements InnovateStudentPointsService {

    @Autowired
    private UserPerInfoService userPerInfoService;

    /**
     * 查询列表
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        EntityWrapper<InnovateStudentPointsEntity> entityWrapper = new EntityWrapper<>();

        if (params.get("key")!=null)entityWrapper.like("stu_num",params.get("key").toString());

        //根据学号查询&&非管理员
        if (params.get("userId")!=null){
            UserPersonInfoEntity user = userPerInfoService.queryUserByUserId(Long.parseLong(params.get("userId").toString()));
            entityWrapper.eq("stu_num",user.getPerStuNo());
        }

        Page<InnovateStudentPointsEntity> page = this.selectPage(
                new Query<InnovateStudentPointsEntity>(params).getPage(),entityWrapper
        );

        return new PageUtils(page);
    }

    /**
     * 查询总积分
     * @param params
     * @return
     */
    @Override
    public Integer queryPointByParams(Map<String, Object> params) {

        EntityWrapper<InnovateStudentPointsEntity> entityWrapper = new EntityWrapper<>();

        UserPersonInfoEntity user = userPerInfoService.queryUserByUserId(Long.parseLong(params.get("userId").toString()));

        entityWrapper.eq("stu_num",user.getPerStuNo());

        List<InnovateStudentPointsEntity> innovateStudentPointsEntities = this.selectList(entityWrapper);

        return innovateStudentPointsEntities.stream().mapToInt(value -> value.getPoints()).sum();
    }

    @Override
    public R insertAndCheck(InnovateStudentPointsEntity innovateStudentPoints) {
        //判断是否已经签到

        EntityWrapper<InnovateStudentPointsEntity> entityWrapper = new EntityWrapper<>();

        entityWrapper.eq("activity_id",innovateStudentPoints.getActivityId());
        entityWrapper.eq("stu_num",innovateStudentPoints.getStuNum());

        InnovateStudentPointsEntity innovateStudentPointsEntity = this.selectOne(entityWrapper);

        if (innovateStudentPointsEntity==null){
            innovateStudentPoints.setOperationTime(new Timestamp(System.currentTimeMillis()));
            insert(innovateStudentPoints);
            return R.ok();
        }else return R.error("你已经签到过了，请勿重复签到");
    }


}