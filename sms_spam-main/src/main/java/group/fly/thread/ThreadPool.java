package group.fly.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPool {

	static ThreadPoolExecutor executor = null;

	@SuppressWarnings("rawtypes")
	public ThreadPool() {
		// TODO Auto-generated constructor stub
		LinkedBlockingQueue queue = new LinkedBlockingQueue<>(20);
		executor = new ThreadPoolExecutor(50,
				50, 60, TimeUnit.SECONDS,
				queue, new RejectedExecutionHandlerImpl());
		executor.allowCoreThreadTimeOut(true);
		// executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
		// @Override
		// public void rejectedExecution(Runnable r,
		// ThreadPoolExecutor executor_) {
		// try {
		// /*
		// * This does the actual put into the queue. Once the max
		// * threads have been reached, the tasks will then queue up.
		// */
		// executor_.getQueue().put(r);
		//
		// } catch (InterruptedException e) {
		// Thread.currentThread().interrupt();
		// return;
		// }
		// }
		// });
		instance = this;
	}

	private static ThreadPool instance = null;

	public static ThreadPool GetInstance() {
		if (instance == null)
			new ThreadPool();
		return instance;
	}

	public ThreadPoolExecutor getThreadExecutor() {
		return executor;
	}

	public void execute(Runnable runable) {
		System.out.println("ActiveCount=" + executor.getActiveCount());
		executor.execute(runable);
	}

	public void stop() {
		executor.shutdown();
	}

	public void cleanPool() {
		executor.shutdown();
	}

	public void getThreadPoolInfo() {
		System.out.println("Total complete Thread="
				+ executor.getCompletedTaskCount());
		System.out.println("Current Pool size=" + executor.getPoolSize());
		System.out.println("Get task count=" + executor.getTaskCount());
		int activeCount = executor.getActiveCount();
		System.out.println("Get active count=" + executor.getActiveCount());
		int queueSize = executor.getQueue().size();
		System.out.println("Get queue size=" + executor.getQueue().size());

		int notcomplete = queueSize - activeCount;
		while (notcomplete > 0) {
			System.out.println("Not complete=" + notcomplete);
			activeCount = executor.getActiveCount();
			queueSize = executor.getQueue().size();
			notcomplete = queueSize - activeCount;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getThreadPoolInfo2() {
		String info = "";
		ThreadPoolInfo poolInfo = new ThreadPoolInfo();
		poolInfo.setPoolName("ThreadPool");
		// System.out.println("Total complete Thread="
		// + executor.getCompletedTaskCount());
		poolInfo.setComplateTask(executor.getCompletedTaskCount());

		// System.out.println("Current Pool size=" + executor.getPoolSize());
		poolInfo.setPoolSize(executor.getPoolSize());
		System.out.println("Get task count=" + executor.getTaskCount());
		poolInfo.setTaskCount(executor.getTaskCount());
		int activeCount = executor.getActiveCount();
		poolInfo.setActiveCount(activeCount);
		// System.out.println("Get active count=" + executor.getActiveCount());
		int queueSize = executor.getQueue().size();
		poolInfo.setQueueSize(queueSize);
		// System.out.println("Get queue size=" + executor.getQueue().size());

		Long notcomplete = (long) (queueSize + activeCount);
		poolInfo.setNot_complete_task(notcomplete);
		info = poolInfo.toString();
		return info;
	}

}
