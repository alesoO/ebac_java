package primary;

public class Fisica extends Pessoa {
	private Long cpf;
	private String surname;
	private Long income;
	private Integer elementsFamily;
	private String religion;
	
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Long getIncome() {
		return income;
	}
	public void setIncome(Long income) {
		this.income = income;
	}
	public Integer getElementsFamily() {
		return elementsFamily;
	}
	public void setElementsFamily(Integer elementsFamily) {
		this.elementsFamily = elementsFamily;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
}
