package com.matus.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Long timeStamp, Integer status, String error, String message, String path) {
        super(timeStamp, status, error, message, path);
    }

    public List<FieldMessage> getErrors() {
		return errors;
	}
	
	//Vai adicionar um error pro vez
	public void addError(String fieldName, String msg) {
		errors.add(new FieldMessage(fieldName, msg));
	}
}
