package com.excilys.formation.computerDatabase.entites;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class Computer {
	
	private int id;
	private String name;	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate introduced;	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate discontinued;
	private Company company;
	
	public Computer () {}
	
	public Computer(int id, String name, LocalDate introduced, LocalDate discontinued,
			Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
}
