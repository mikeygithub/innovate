package com.innovate.modules.points.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-11-10 13:11:17
 */
@Data
@TableName("innovate_student_activity")
public class InnovateStudentActivityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 活动名称
	 */
	private String activityName;
	/**
	 * 活动地点
	 */
	private String activitySite;
	/**
	 * 活动学分
	 */
	private Integer activityPoint;
	/**
	 * 活动结束时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date activityEndTime;

	/**
	 * 活动开始时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date activityTime;

	private String activitySignQr;

	private String activityPointQr;


}
