package group.fly.entities;

public class PaycodeInquiryBERs {

	public int ReturnCode;
	public long PurchasingPrice;
	public long ReferPrice;
	public int MaxQuantity;
	public String DescriptionCode;

	public PaycodeInquiryBERs() {
	}

	public PaycodeInquiryBERs(int returnCode, long purchasingPrice, long referPrice, int maxQuantity,
			String descriptionCode) {
		ReturnCode = returnCode;
		PurchasingPrice = purchasingPrice;
		ReferPrice = referPrice;
		MaxQuantity = maxQuantity;
		DescriptionCode = descriptionCode;
	}

	public int getReturnCode() {
		return ReturnCode;
	}

	public void setReturnCode(int returnCode) {
		ReturnCode = returnCode;
	}

	public long getPurchasingPrice() {
		return PurchasingPrice;
	}

	public void setPurchasingPrice(long purchasingPrice) {
		PurchasingPrice = purchasingPrice;
	}

	public long getReferPrice() {
		return ReferPrice;
	}

	public void setReferPrice(long referPrice) {
		ReferPrice = referPrice;
	}

	public int getMaxQuantity() {
		return MaxQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		MaxQuantity = maxQuantity;
	}

	public String getDescriptionCode() {
		return DescriptionCode;
	}

	public void setDescriptionCode(String descriptionCode) {
		DescriptionCode = descriptionCode;
	}

	@Override
	public String toString() {
		return "QueryPriceRes [ReturnCode=" + ReturnCode + ", PurchasingPrice=" + PurchasingPrice + ", ReferPrice="
				+ ReferPrice + ", MaxQuantity=" + MaxQuantity + ", DescriptionCode=" + DescriptionCode + "]";
	}
}
