package utils;

import com.mysql.jdbc.StringUtils;

public class OptionsRequest {
	
	private enum OrderWay {
		ASC, DESC
	}
	
	private enum OrderColumn {
		NAME("c.name"), INTRODUCED("c.introduced"), DISCONTINUED("c.discontinued"), COMPANY_NAME("cy.name");
		
		private String field;
		
		private OrderColumn (String field) {
			this.field = field;
		}
		
		public String toString() {
			return field;
		}
	}
	
	private String nameFilter;
	private OrderWay orderW;
	private OrderColumn orderC;
	
	public OptionsRequest(String nameFilter, int sort) {
		
		if (!StringUtils.isNullOrEmpty(nameFilter))
			this.nameFilter = new StringBuilder().append("%").append(nameFilter).append("%").toString();
		
		setSort(sort);
	
	}

	private void setSort (int sort) {
		
		if (sort < 0)
			this.orderW = OrderWay.DESC;	
		else
			this.orderW = OrderWay.ASC;	
		
		switch (Math.abs(sort)) {
			case 2:
				this.orderC = OrderColumn.INTRODUCED;
				break;
			case 3:
				this.orderC = OrderColumn.DISCONTINUED;
				break;
			case 4:
				this.orderC = OrderColumn.COMPANY_NAME;
				break;
			default:
				this.orderC = OrderColumn.NAME;	
				
		}
	}
	
	public String getNameFilter() {
		return nameFilter;
	}

	public String getOrderW() {
		return orderW.toString();
	}

	public String getOrderC() {
		return orderC.toString();
	}
}
