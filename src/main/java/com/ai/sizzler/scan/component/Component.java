package com.ai.sizzler.scan.component;

import java.util.Map;

/**
 * 扫描组件，为了实现兼容多种类型的数据类型
 * @author Administrator
 */
public interface Component{
	/**
	 * 通过sql查询到的map对象，装载数据
	 * @param map
	 */
	void load(Map map);
	
}
