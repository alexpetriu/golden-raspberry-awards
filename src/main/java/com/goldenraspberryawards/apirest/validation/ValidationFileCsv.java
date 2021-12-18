package com.goldenraspberryawards.apirest.validation;

import java.util.List;

public class ValidationFileCsv {
	
	public void validateLine(String[] fields) throws Exception {
		
		int maxLengh = 5;
		
		if (fields.length > maxLengh) {
			throw new Exception("Número de colunas excede o esperado. Esperado: " + maxLengh + ". Obtido: " + fields.length);
		}
		
		try {
			Integer year = Integer.valueOf(fields[0]);
			
		} catch (NumberFormatException ex) {
			throw new Exception("Formato no indice 'Year' inválido. Esperado Integer.");
		}
		
	}
	
	public List<String[]> validateData(List<String[]> lines) throws Exception {
		
		lines.remove(0);
		
		for (String[] line : lines) {
			validateLine(line);
		}
		
		return lines;
		
	}
}
