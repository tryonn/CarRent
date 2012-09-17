package br.ufpe.cin.amadeus.struts.util;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class FieldValidator {

	private ActionMessages messages;
	
	
	public FieldValidator(ActionMessages messages) {
		this.messages = messages;
	}
	
	public void validateSSN(String ssn, String ssnDigit) {
		if (isValid(ssn) ^ isValid(ssnDigit)) {
			messages.add("", new ActionMessage("errors.ssn.incomplete"));
			return;
		}
		if (!isValid(ssn, 9) && !isValid(ssn)) {
			messages.add("", new ActionMessage("errors.ssn.invalidLength"));
		}
		if (!isValid(ssnDigit, 2) && !isValid(ssnDigit)) {
			messages.add("", new ActionMessage("errors.ssn.invalidDigitLength"));
		}
	}
	
	public void validateCity(String city){
		if(city == null || city.startsWith(" ") || city.indexOf("  ") != -1){
			messages.add("", new ActionMessage("errors.city.invalid"));
		}
	}
	
	public void validateInstitution(String institution){
		if(institution == null || institution.startsWith(" ") || institution.indexOf("  ") != -1){
			messages.add("", new ActionMessage("errors.instituition.invalid"));
		}
	}
	
	public void validateCurriculum(String curriculum){
		if(curriculum == null || curriculum.startsWith(" ") || curriculum.indexOf("  ") != -1){
			messages.add("", new ActionMessage("errors.curriculum.invalid"));
		}
	}
	
	public void validatePhone(String ddd, String phoneNumber) {
		if (isValid(ddd) ^ isValid(phoneNumber)) {
			messages.add("", new ActionMessage("errors.phone.incomplete"));
			return;
		}
		if (!isValid(ddd, 2) && !isValid(ddd)) {
			messages.add("", new ActionMessage("errors.phone.invalidDigitLength"));
		}
		if (!isValid(phoneNumber, 8) && !isValid(phoneNumber)) {
			messages.add("", new ActionMessage("errors.phone.invalidLength"));
		}
	}
	
	private boolean isValid(String value) {
		return isValid(value, 0);
	}
	private boolean isValid(String value, int size) {
		return (value != null && value.trim().length() == size);
	}
}
