package com.ai.sizzler.scan.component;
/**
 * 导出
 * @author Administrator
 *
 * @param <T>
 */
public interface Exporter extends Component {
	/**
	 * 初始化
	 */
	void init();
	/**
	 * 销毁资源
	 */
	void destroy();
	/**
	 * 处理数据，线程内部调用
	 * @param record
	 */
	void deal(Object record);
}
