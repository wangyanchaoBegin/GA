package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-04 22:55:43
 */
@Data
@TableName("ga_task")
public class GaTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * ga任务名称
	 */
	private String name;
	/**
	 * 任务参数，使用‘ ，’间隔
	 */
	private String params;
	/**
	 * 任务状态
	 */
	private String state;
	/**
	 * 
	 */
	private String info;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	/**
	 *
	 */
	private String createUser;
}
