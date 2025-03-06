package br.com.primary.main.domain.jpa;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Persistente {
	public Long getId();
	public void setId(Long id);
}
