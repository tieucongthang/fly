package group.fly.entities;

import com.google.gson.Gson;

public class Test {

	private void Main() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		String json = "{\r\n"
				+ "  \"PaycodeInquiryBEResult\": {\r\n"
				+ "    \"ReferPrice\": 10000,\r\n"
				+ "    \"ReturnCode\": 0,\r\n"
				+ "    \"PurchasingPrice\": 10000,\r\n"
				+ "    \"MaxQuantity\": 0\r\n"
				+ "  }\r\n"
				+ "}";
		
		sampleClass obj = (sampleClass)new Gson().fromJson(json, sampleClass.class);
		
		System.out.println(obj.getPaycodeInquiryBEResult().toString());
	}
}

class sampleClass{
	PaycodeInquiryBERs PaycodeInquiryBEResult;

	public PaycodeInquiryBERs getPaycodeInquiryBEResult() {
		return PaycodeInquiryBEResult;
	}

	public void setPaycodeInquiryBEResult(PaycodeInquiryBERs paycodeInquiryBEResult) {
		PaycodeInquiryBEResult = paycodeInquiryBEResult;
	}
}