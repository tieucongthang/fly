/**
 * SoftpinObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package group.fly.entities;

import java.io.Serializable;

public class SoftpinObject implements Serializable{
	private int softpinId;
	private String softpinSerial;
	private String softpinPinCode;
	private String expiryDate;
	
	
	
	
	
	
	@Override
	public String toString() {
		return "SoftpinObject [softpinId=" + softpinId + ", softpinSerial="
				+ softpinSerial + ", expiryDate=" + expiryDate + "]";
	}
	
	
	public int getSoftpinId() {
		return softpinId;
	}
	public void setSoftpinId(int softpinId) {
		this.softpinId = softpinId;
	}
	public String getSoftpinSerial() {
		return softpinSerial;
	}
	public void setSoftpinSerial(String softpinSerial) {
		this.softpinSerial = softpinSerial;
	}
	public String getSoftpinPinCode() {
		return softpinPinCode;
	}
	public void setSoftpinPinCode(String softpinPinCode) {
		this.softpinPinCode = softpinPinCode;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}   
}
