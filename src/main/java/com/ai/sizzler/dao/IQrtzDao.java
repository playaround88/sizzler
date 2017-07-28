package com.ai.sizzler.dao;

import java.util.Map;

import com.ai.commons.pager.PagedList;

public interface IQrtzDao {
	PagedList<Map> queryTaskList(Map params);
}
