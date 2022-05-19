package group.fly.thread;

public class BaseClass {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		// info("finalize: " + this.hashCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return GsonUltilities.toJson(this);
//	}

//	FileLogger fileloger = null;

	public BaseClass() {
		super();
		// TODO Auto-generated constructor stub
//		if (fileloger == null) {
//			fileloger = new FileLogger(this.getClass());
//		}
		// info("init: " + this.hashCode());
	}

	

}
