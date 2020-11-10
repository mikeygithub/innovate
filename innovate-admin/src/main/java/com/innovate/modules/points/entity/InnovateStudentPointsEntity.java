package com.innovate.modules.points.entity;


import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 
 * 
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-08-09 09:13:27
 */
@Data
@TableName("innovate_student_points")
public class InnovateStudentPointsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ExcelIgnore
	@TableId
	private Long id;
	/**
	 * 学号
	 */
	@ExcelProperty("学号")
	private String stuNum;
	/**
	 * 添加分数
	 */
	@ExcelProperty("积分")
	private Integer points;
	/**
	 * 加分原因
	 */
	@ExcelProperty("加分原因")
	private String pointsReason;

	/**
	 * 操作时间
	 */
	@ExcelIgnore
	private Date operationTime;

	private Integer activityId;

}
