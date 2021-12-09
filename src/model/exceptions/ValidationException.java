package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	//Cole��o contendo todos os erros poss�eis | Map = Nome do campo,mensagem de erro
	private Map<String,String> errors = new HashMap<>();

	
	public ValidationException(String msg){
		super(msg);
	}	
	
	//M�todo para ter acesso aos erros
	public Map<String,String> getErrors(){
		return errors;
	}
	
	//M�todo para adicionar erros
	public void addError(String fieldName,String errorMessage) {
		errors.put(fieldName, errorMessage);
	}
}
