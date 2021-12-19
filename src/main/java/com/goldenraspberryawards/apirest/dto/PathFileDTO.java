package com.goldenraspberryawards.apirest.dto;

public class PathFileDTO {
	
	private String pathFile;
	
	public PathFileDTO() {
		//default constructor
	}
	
	public PathFileDTO(String pathFile) {
		this.pathFile = pathFile;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	
	
}
