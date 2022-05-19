package group.fly.config;

public class ProcessConfig {

	String listStockCode;
	String acceptIncreasePercent;
	String acceptDecreasePercent;

	public String getAcceptIncreasePercent() {
		return acceptIncreasePercent;
	}

	public void setAcceptIncreasePercent(String acceptIncreasePercent) {
		this.acceptIncreasePercent = acceptIncreasePercent;
	}

	public String getAcceptDecreasePercent() {
		return acceptDecreasePercent;
	}

	public void setAcceptDecreasePercent(String acceptDecreasePercent) {
		this.acceptDecreasePercent = acceptDecreasePercent;
	}

	public String getListStockCode() {
		return listStockCode;
	}

	public void setListStockCode(String listStockCode) {
		this.listStockCode = listStockCode;
	}
	
}
