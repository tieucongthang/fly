package group.fly.mongo.entities;

public class TblBorrower {
	String borrower_name;
	String borrower_mobile;
	String id_number;
	Integer borrower_type;
	public String getBorrower_name() {
		return borrower_name;
	}
	public void setBorrower_name(String borrower_name) {
		this.borrower_name = borrower_name;
	}
	public String getBorrower_mobile() {
		return borrower_mobile;
	}
	public void setBorrower_mobile(String borrower_mobile) {
		this.borrower_mobile = borrower_mobile;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public Integer getBorrower_type() {
		return borrower_type;
	}
	public void setBorrower_type(Integer borrower_type) {
		this.borrower_type = borrower_type;
	}
}
