package group.fly.mongo.entities;

public class TblBlackList {

	Integer black_list_id;
	String borrower_name;
	String borrower_id_number;
	public Integer getBlack_list_id() {
		return black_list_id;
	}
	public void setBlack_list_id(Integer black_list_id) {
		this.black_list_id = black_list_id;
	}
	public String getBorrower_name() {
		return borrower_name;
	}
	public void setBorrower_name(String borrower_name) {
		this.borrower_name = borrower_name;
	}
	public String getBorrower_id_number() {
		return borrower_id_number;
	}
	public void setBorrower_id_number(String borrower_id_number) {
		this.borrower_id_number = borrower_id_number;
	}
}
