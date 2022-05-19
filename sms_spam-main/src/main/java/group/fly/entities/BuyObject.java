/**
 * BuyObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package group.fly.entities;

import java.io.Serializable;
import java.util.Arrays;

public class BuyObject implements Serializable {
	 private int productId;
	 private int productValue;
	 private String categoryName;
	 private String serviceProviderName;
	 private float commission;
	 private SoftpinObject[] softpins;
	 
	 
	 
	@Override
	public String toString() {
		return "BuyObject [productId=" + productId + ", productValue="
				+ productValue + ", categoryName=" + categoryName
				+ ", serviceProviderName=" + serviceProviderName
				+ ", commission=" + commission + ", softpins="
				+ Arrays.toString(softpins) + "]";
	}
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductValue() {
		return productValue;
	}
	public void setProductValue(int productValue) {
		this.productValue = productValue;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getServiceProviderName() {
		return serviceProviderName;
	}
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
	public float getCommission() {
		return commission;
	}
	public void setCommission(float commission) {
		this.commission = commission;
	}
	public SoftpinObject[] getSoftpins() {
		return softpins;
	}
	public void setSoftpins(SoftpinObject[] softpins) {
		this.softpins = softpins;
	}
	
	
	
	public static void main(String[] args) {
		SoftpinObject arr_softpins[] = new SoftpinObject[3];
		SoftpinObject s1 = new SoftpinObject();
		SoftpinObject s2 = new SoftpinObject();
		arr_softpins[0] = s1;
		arr_softpins[1] = s2;
		System.out.println(Arrays.toString(arr_softpins));
	}
}
