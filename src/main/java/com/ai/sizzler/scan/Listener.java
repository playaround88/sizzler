package com.ai.sizzler.scan;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Listener extends Thread {
	private WeakReference<Task> task;
	private ExecutorService workers;
	private boolean loop = true;
	
	public Listener(Task task,ExecutorService workers){
		this.task=new WeakReference<Task>(task);
		this.workers=workers;
	}

	@Override
	public void run() {
		while (loop) {
			try {
				List<Object> records = task.get().getImporter().scan(task.get().getFetchSize());

				if (records == null || records.size() == 0) {
					Thread.currentThread().sleep(task.get().getSleepTime() * 1000);
					continue;
				}

				// 遍历此次fetch的数据
				for (final Object record : records) {
					deal(record);
				}

				// 休眠1秒，等待线程处理，避免线程池压力过大
				Thread.currentThread().sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void shutdown() {
		this.loop = false;
	}

	private void deal(final Object record) {
		// 可能会传回null值
		if (record == null)
			return;

		int update = task.get().getImporter().updateState(record);
		// 如果更新成功
		if (update == 1) {
			// dispatcher.mark();
			// 提交到线程池处理
			workers.submit(new Runnable() {
				@Override
				public void run() {
					task.get().getExporter().deal(record);
				}
			});
		}
	}

}
