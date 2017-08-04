package com.ai.sizzler.dao;

import java.util.HashMap;
import java.util.Map;

import com.ai.commons.pager.PagedList;
import com.ai.sizzler.domain.ImporterForm;

public interface IImporterDao {
	/**
	 * 保存记录
	 * @param imp
	 * @return
	 */
	int insert(ImporterForm imp);
	/**
	 * 通过id删除记录
	 * @param id
	 */
	int del(long id);
	/**
	 * 更新记录
	 * @param ds
	 */
	int update(ImporterForm imp);
	/**
	 * 通过id查询单条记录
	 * @param id
	 * @return
	 */
	Map selectById(long id);
	/**
	 * 通过条件查询分页的记录列表
	 * @param params
	 * @return
	 */
	PagedList<Map> selectPagedList(HashMap params);
}
