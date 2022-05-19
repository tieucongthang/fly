package group.fly.thread;

public class ThreadPoolInfo extends BaseClass {

	/**
	 * @return the complateTask
	 */
	public long getComplateTask() {
		return complateTask;
	}

	/**
	 * @param complateTask
	 *            the complateTask to set
	 */
	public void setComplateTask(long complateTask) {
		this.complateTask = complateTask;
	}

	/**
	 * @return the poolSize
	 */
	public long getPoolSize() {
		return poolSize;
	}

	/**
	 * @param poolSize
	 *            the poolSize to set
	 */
	public void setPoolSize(long poolSize) {
		this.poolSize = poolSize;
	}

	/**
	 * @return the taskCount
	 */
	public long getTaskCount() {
		return taskCount;
	}

	/**
	 * @param taskCount
	 *            the taskCount to set
	 */
	public void setTaskCount(long taskCount) {
		this.taskCount = taskCount;
	}

	/**
	 * @return the activeCount
	 */
	public long getActiveCount() {
		return activeCount;
	}

	/**
	 * @param activeCount
	 *            the activeCount to set
	 */
	public void setActiveCount(long activeCount) {
		this.activeCount = activeCount;
	}

	/**
	 * @return the queueSize
	 */
	public long getQueueSize() {
		return queueSize;
	}

	/**
	 * @param queueSize
	 *            the queueSize to set
	 */
	public void setQueueSize(long queueSize) {
		this.queueSize = queueSize;
	}

	/**
	 * @return the not_complete_task
	 */
	public long getNot_complete_task() {
		return not_complete_task;
	}

	/**
	 * @param not_complete_task
	 *            the not_complete_task to set
	 */
	public void setNot_complete_task(long not_complete_task) {
		this.not_complete_task = not_complete_task;
	}

	/**
	 * @return the poolName
	 */
	public String getPoolName() {
		return poolName;
	}

	/**
	 * @param poolName
	 *            the poolName to set
	 */
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	String poolName;
	long activeCount;
	long queueSize;
	long complateTask;
	long poolSize;
	long taskCount;
	long not_complete_task;

}
