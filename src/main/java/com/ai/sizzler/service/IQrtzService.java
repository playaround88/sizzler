package com.ai.sizzler.service;

import java.util.Map;

import com.ai.commons.pager.PagedList;

public interface IQrtzService {
	PagedList<Map> queryTaskList(Map params);
}
