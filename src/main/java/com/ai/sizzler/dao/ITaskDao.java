package com.ai.sizzler.dao;

import java.util.HashMap;

import com.ai.commons.pager.PagedList;
import com.ai.sizzler.scan.Task;

public interface ITaskDao {
	/**
	 * 保存记录
	 * @param ds
	 * @return
	 */
	int insert(Task task);
	/**
	 * 通过id删除记录
	 * @param id
	 */
	int del(long id);
	/**
	 * 更新记录
	 * @param ds
	 */
	int update(Task task);
	/**
	 * 通过id查询单条记录
	 * @param id
	 * @return
	 */
	Task selectById(long id);
	/**
	 * 通过条件查询分页的记录列表
	 * @param params
	 * @return
	 */
	PagedList<Task> selectPagedList(HashMap params);
}
