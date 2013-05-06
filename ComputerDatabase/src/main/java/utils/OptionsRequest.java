package utils;

import com.mysql.jdbc.StringUtils;

public class OptionsRequest {
	
	public enum OrderWay {
		ASC, DESC
	}
	
	public enum OrderColumn {
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
		super();		
		
		if (StringUtils.isNullOrEmpty(nameFilter))
			this.nameFilter = "%";
		else
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
				this.orderW = OrderWay.ASC;
		}
	}
	
	public String getNameFilter() {
		return nameFilter;
	}

	public OrderWay getOrderW() {
		return orderW;
	}

	public OrderColumn getOrderC() {
		return orderC;
	}
}
