/**
 * 
 */
package group.fly.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Tieu Cong Thang
 * 
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		// TODO Auto-generated method stub
		System.out.println("ANOTHER THREAD BE REJECTED");
		try {
			executor.purge();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
