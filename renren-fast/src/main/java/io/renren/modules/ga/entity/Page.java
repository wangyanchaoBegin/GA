package io.renren.modules.ga.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-04 22:55:43
 */
@Data
//@TableName("ga_task")
public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<T> data;

	private Integer count;

	public Page(List<T> data,Integer count){
		this.data = data == null ? new ArrayList<T>() : data;
		this.count = count;
	}



}
