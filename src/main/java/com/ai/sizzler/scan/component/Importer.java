package com.ai.sizzler.scan.component;

import java.util.List;
/**
 * 导入
 * @author Administrator
 *
 * @param <T>
 */
public interface Importer extends Component{
	/**
	 * 初始化数据连接
	 */
	void init();
	/**
	 * 销毁资源链接
	 */
	void destroy();
	/**
	 * 扫描得到指定数量的数据
	 * @param size
	 * @return
	 */
	List<Object> scan(int size);
	/**
	 * 更新数据状态，锁定数据
	 * 返回1表示锁定成功，否则表示锁定是吧，系统不会处理该数据
	 * @param record
	 * @return
	 */
	int updateState(Object record);
}
