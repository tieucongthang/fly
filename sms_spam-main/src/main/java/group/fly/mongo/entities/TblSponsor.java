package group.fly.mongo.entities;

public class TblSponsor {
	String sponsor_name;
	String sponsor_mobile;
	Integer sponsor_status;
	
	public String getSponsor_name() {
		return sponsor_name;
	}
	public void setSponsor_name(String sponsor_name) {
		this.sponsor_name = sponsor_name;
	}
	public String getSponsor_mobile() {
		return sponsor_mobile;
	}
	public void setSponsor_mobile(String sponsor_mobile) {
		this.sponsor_mobile = sponsor_mobile;
	}
	public Integer getSponsor_status() {
		return sponsor_status;
	}
	public void setSponsor_status(Integer sponsor_status) {
		this.sponsor_status = sponsor_status;
	}
}
