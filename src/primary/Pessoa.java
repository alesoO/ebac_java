package primary;

import java.util.Date;

public abstract class Pessoa {
	private String name;
	private Date born_date;
	private String address;
	private String legal_situation;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBorn_date() {
		return born_date;
	}
	public void setBorn_date(Date born_date) {
		this.born_date = born_date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLegal_situation() {
		return legal_situation;
	}
	public void setLegal_situation(String legal_situation) {
		this.legal_situation = legal_situation;
	}
}
