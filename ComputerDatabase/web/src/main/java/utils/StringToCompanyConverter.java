package utils;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.computerDatabase.serviceAPI.ComputerService;
import com.mysql.jdbc.StringUtils;

public class StringToCompanyConverter extends PropertyEditorSupport{

	@Autowired
	private ComputerService service;
	
	public StringToCompanyConverter () {}
	
	public StringToCompanyConverter (ComputerService service) {
		this.service = service;
	}

	public void setService(ComputerService service) {
		this.service = service;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.isNullOrEmpty(text)) {
			int idCompany = Integer.parseInt(text);
			setValue(service.getCompany(idCompany));
		}
		else 
			setValue(null);
	}
}
