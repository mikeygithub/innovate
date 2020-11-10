package com.innovate.modules.points.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.innovate.common.utils.Constant;
import com.innovate.common.utils.OSSUtils;
import com.innovate.modules.points.utils.QRCodeUtil;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.Map;
import com.innovate.common.utils.PageUtils;
import com.innovate.common.utils.Query;
import com.innovate.modules.points.dao.InnovateStudentActivityDao;
import com.innovate.modules.points.entity.InnovateStudentActivityEntity;
import com.innovate.modules.points.service.InnovateStudentActivityService;


@Service("innovateStudentActivityService")
public class InnovateStudentActivityServiceImpl extends ServiceImpl<InnovateStudentActivityDao, InnovateStudentActivityEntity> implements InnovateStudentActivityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InnovateStudentActivityEntity> page = this.selectPage(
                new Query<InnovateStudentActivityEntity>(params).getPage(),
                new EntityWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public boolean insert(InnovateStudentActivityEntity entity) {
        super.insert(entity);
        //签到二维码
        InputStream signImage = QRCodeUtil.createImage(Constant.PROTOCOL_IP_PORT+"/#/sign?activity="+entity.getId());
        //获取学分二维码
        InputStream pointImage = QRCodeUtil.createImage(Constant.PROTOCOL_IP_PORT+"/#/out?activity="+entity.getId());
        //保存到OSS
        String signImagePath = OSSUtils.upload2OSS(Constant.QR_CODE_PATH + System.currentTimeMillis() + ".png", signImage);
        entity.setActivitySignQr(signImagePath);
        String pointImagePath = OSSUtils.upload2OSS(Constant.QR_CODE_PATH + System.currentTimeMillis() + ".png", pointImage);
        entity.setActivityPointQr(pointImagePath);
        return this.updateById(entity);
    }
}