package primary;

public class Juridica extends Pessoa {
	private Long cnpj;
	private String fantasyName;
	private Double invoicing;
	private Integer numberOfEmployees;
	private String economicActivity;
	
	public Long getCnpj() {
		return cnpj;
	}
	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}
	public String getFantasyName() {
		return fantasyName;
	}
	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}
	public Double getInvoicing() {
		return invoicing;
	}
	public void setInvoicing(Double invoicing) {
		this.invoicing = invoicing;
	}
	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}
	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	public String getEconomicActivity() {
		return economicActivity;
	}
	public void setEconomicActivity(String economicActivity) {
		this.economicActivity = economicActivity;
	}
	
}
