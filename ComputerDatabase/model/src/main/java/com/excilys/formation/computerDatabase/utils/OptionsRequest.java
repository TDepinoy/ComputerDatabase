package com.excilys.formation.computerDatabase.utils;


public class OptionsRequest {
	
	private enum OrderWay {
		ASC, DESC
	}
	
	private enum OrderColumn {
		NAME("c.name"), INTRODUCED("c.introduced"), DISCONTINUED("c.discontinued"), COMPANY_NAME("cy.name");
		
		private final String field;
		
		private OrderColumn (String field) {
			this.field = field;
		}
		
		public String toString() {
			return field;
		}
	}
	
	private final String[] CRITERIA = {OrderColumn.NAME.toString(), 
			OrderColumn.INTRODUCED.toString(), 
			OrderColumn.DISCONTINUED.toString(),
			OrderColumn.COMPANY_NAME.toString(), 
			OrderColumn.NAME.toString()};
	
	private String nameFilter;
	private String orderW;
	private String orderC;
	
	public OptionsRequest(String nameFilter, int sort) {
		
		if (nameFilter != null && !nameFilter.isEmpty())
			this.nameFilter = new StringBuilder().append("%").append(nameFilter).append("%").toString();
		
		setSort(sort);
	
	}

	private void setSort (int sort) {
		
		if (sort < 0)
			this.orderW = OrderWay.DESC.toString();	
		else
			this.orderW = OrderWay.ASC.toString();	
		
		if((sort > 4 || sort < -4) || (sort == 0)) 
			this.orderC = CRITERIA[0];
		else 
			this.orderC = CRITERIA[Math.abs(sort)-1];
	}
	
	public String getNameFilter() {
		return nameFilter;
	}

	public String getOrderW() {
		return orderW;
	}

	public String getOrderC() {
		return orderC;
	}
}
