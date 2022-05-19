package group.fly.entities;

import com.google.gson.Gson;

public class MerchantRes {

	public MerchantRes() {
		// TODO Auto-generated constructor stub
	}

	private int errorCode;
	private String errorMessage;
	private long merchantBalance;
	private BuyObject[] products;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public long getMerchantBalance() {
		return merchantBalance;
	}

	public void setMerchantBalance(long merchantBalance) {
		this.merchantBalance = merchantBalance;
	}

	public BuyObject[] getProducts() {
		return products;
	}

	public void setProducts(BuyObject[] products) {
		this.products = products;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public int getSysTransId() {
		return sysTransId;
	}

	public void setSysTransId(int sysTransId) {
		this.sysTransId = sysTransId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	private String requestID;
	private int sysTransId;
	private String signature;

	public static void main(String[] args) {
		MerchantRes merchantReqObj = new MerchantRes();
		// merchantReqObj.setOperation(1000);
		BuyItem byBuyItem1 = new BuyItem(1, 5);
		BuyItem byBuyItem2 = new BuyItem(3, 7);
		BuyItem byBuyItem3 = new BuyItem(5, 7);
		BuyItem[] byBuyItems = new BuyItem[3];
		byBuyItems[0] = byBuyItem1;
		byBuyItems[1] = byBuyItem2;
		byBuyItems[2] = byBuyItem3;
		// merchantReqObj.setBuyItems(byBuyItems);

		Gson gson = new Gson();
		String req = gson.toJson(merchantReqObj);
		System.out.println("JSON1=" + req);
		MerchantRes merchantReqObj2 = (MerchantRes) gson.fromJson(req, MerchantRes.class);
		System.out.println(merchantReqObj2.toString());

	}
}
