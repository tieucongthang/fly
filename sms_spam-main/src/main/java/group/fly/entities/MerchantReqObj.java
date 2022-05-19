package group.fly.entities;

import java.util.Arrays;

import com.google.gson.Gson;

public class MerchantReqObj {

	public MerchantReqObj(int operation, String username, BuyItem[] buyItems, String requestID, String keyBirthdayTime,
			String targetAccount, String providerCode, int topupAmount, String signature, String token,
			String requestTime) {
		super();
		this.operation = operation;
		this.username = username;
		this.buyItems = buyItems;
		this.requestID = requestID;
		this.keyBirthdayTime = keyBirthdayTime;
		this.targetAccount = targetAccount;
		this.providerCode = providerCode;
		this.topupAmount = topupAmount;
		this.signature = signature;
		this.token = token;
		this.requestTime = requestTime;
	}

	

	@Override
	public String toString() {
		return "MerchantReqObj [operation=" + operation + "\r username=" + username + "\r merchantPass=" + merchantPass
				+ "\r buyItems=" + Arrays.toString(buyItems) + "\r requestID=" + requestID + "\r keyBirthdayTime="
				+ keyBirthdayTime + "\r targetAccount=" + targetAccount + "\r providerCode=" + providerCode
				+ "\r topupAmount=" + topupAmount + "\r signature=" + signature + "\r token=" + token + "\r requestTime="
				+ requestTime + "]";
	}



	public MerchantReqObj() {
		// TODO Auto-generated constructor stub
	}

	private int operation;
	// 1000:download
	// 1100:redownload
	// 1200:topup
	// 1300:query topup result
	// 1400:
	private String username;
	private String merchantPass;

	private BuyItem[] buyItems;
	private String requestID;
	private String keyBirthdayTime;
	private String targetAccount;
	private String providerCode;
	private int topupAmount;
	private String signature;
	private String token;
	private String requestTime;// yyyyMMddHHmmss
	private Integer accountType;

	public Integer getAccountType() {
		return accountType;
	}



	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}



	public String getMerchantPass() {
		return merchantPass;
	}

	public void setMerchantPass(String merchantPass) {
		this.merchantPass = merchantPass;
	}
	
	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BuyItem[] getBuyItems() {
		return buyItems;
	}

	public void setBuyItems(BuyItem[] buyItems) {
		this.buyItems = buyItems;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getKeyBirthdayTime() {
		return keyBirthdayTime;
	}

	public void setKeyBirthdayTime(String keyBirthdayTime) {
		this.keyBirthdayTime = keyBirthdayTime;
	}

	public String getTargetAccount() {
		return targetAccount;
	}

	public void setTargetAccount(String targetAccount) {
		this.targetAccount = targetAccount;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public int getTopupAmount() {
		return topupAmount;
	}

	public void setTopupAmount(int topupAmount) {
		this.topupAmount = topupAmount;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static void main(String[] args) {
		MerchantReqObj merchantReqObj = new MerchantReqObj();
		merchantReqObj.setOperation(1000);
		BuyItem byBuyItem1 = new BuyItem(1, 5);
		BuyItem byBuyItem2 = new BuyItem(3, 7);
		BuyItem byBuyItem3 = new BuyItem(5, 7);
		BuyItem[] byBuyItems = new BuyItem[3];
		byBuyItems[0] = byBuyItem1;
		byBuyItems[1] = byBuyItem2;
		byBuyItems[2] = byBuyItem3;
		merchantReqObj.setBuyItems(byBuyItems);

		Gson gson = new Gson();
		String req = gson.toJson(merchantReqObj);
		System.out.println("JSON1=" + req);
		MerchantReqObj merchantReqObj2 = (MerchantReqObj) gson.fromJson(req, MerchantReqObj.class);
		System.out.println(merchantReqObj2.toString());

	}
}
