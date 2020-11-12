package com.innovate.modules.points.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 签到
 *
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-11-10 13:33:51
 */
@Data
@TableName("innovate_student_sign_in")
public class InnovateStudentSignInEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 活动id
	 */
	private Long activityId;
	/**
	 * 学生学号
	 */
	private Long studentId;
	/**
	 * 签到时间
	 */
	private Date signInTime;

	/**
	 * 对应的活动
	 */
	@TableField(exist = false)
	private InnovateStudentActivityEntity innovateStudentActivityEntity;
}
