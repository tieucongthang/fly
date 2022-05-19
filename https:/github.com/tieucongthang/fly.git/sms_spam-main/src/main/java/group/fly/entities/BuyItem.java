/**
 * BuyItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package group.fly.entities;

import java.io.Serializable;

public class BuyItem implements Serializable {
	private int productId;
	private int quantity;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BuyItem() {
		
	}

	public BuyItem(int productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
}
