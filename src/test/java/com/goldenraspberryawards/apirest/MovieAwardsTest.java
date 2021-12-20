package com.goldenraspberryawards.apirest;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldenraspberryawards.apirest.constants.ConstantsURL;
import com.goldenraspberryawards.apirest.dto.PathFileDTO;
import com.goldenraspberryawards.apirest.resources.MovieAwardsResource;

public class MovieAwardsTest extends ApirestApplicationTests {
	
	private MockMvc mockMvc;
	
	@Autowired
	private MovieAwardsResource movieAwardsResource;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(movieAwardsResource).build();
	}
	
	@Test
	public void importFilePath_ReturnStatus200() throws Exception {
		
		setUp();
		
		PathFileDTO pathFileDto = new PathFileDTO("src/main/resources/csv-files/movielist1.csv");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(pathFileDto);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/" + ConstantsURL.URL_IMPORT_PATH)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json)
        		).andExpect(MockMvcResultMatchers.status().isOk());
		
        
    }
	
	@Test
	public void importFilePath_ReturnStatus400() throws Exception {
		
		setUp();
		
		PathFileDTO pathFileDto = new PathFileDTO("src/main/resources/csv-files/movielist_invalid_1.csv");
		
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writeValueAsString(pathFileDto);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/" + ConstantsURL.URL_IMPORT_PATH)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json)
        		).andExpect(MockMvcResultMatchers.status().isBadRequest());
        
    }
	
	@Test
	public void importFileUpload_ReturnStatus200() throws Exception {
		
		setUp();
		
		String path = "src/main/resources/csv-files/movielist1.csv";
		
		File file = new File(path);
		
		FileInputStream input = new FileInputStream(file);
		
		MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input.readAllBytes());
		
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/" + ConstantsURL.URL_IMPORT_FILE)
        		.file(multipartFile)
        		).andExpect(MockMvcResultMatchers.status().isOk());

    }
	
	@Test
	public void importFileUpload_ReturnStatus400() throws Exception {
		
		setUp();
		
		String path = "src/main/resources/csv-files/movielist_invalid_1.csv";
		
		File file = new File(path);
		
		FileInputStream input = new FileInputStream(file);
		
		MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input.readAllBytes());
		
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/" + ConstantsURL.URL_IMPORT_FILE)
        		.file(multipartFile)
        		).andExpect(MockMvcResultMatchers.status().isBadRequest());

		input.close();
		
    }
	
	@Test
	public void getMinMaxIntervalAwards_ReturnStatus200() throws Exception {
		
		setUp();
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/" + ConstantsURL.URL_INTERVAL_WINS)
        		.contentType(MediaType.APPLICATION_JSON)
        		).andExpect(MockMvcResultMatchers.status().isOk());
		
    }
	
	@Test
	public void getWinnersByYear_ReturnStatus200() throws Exception {
		
		setUp();
		
		String year = "1980";
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/" + ConstantsURL.URL_WINNERS + "/"+ year)
        		.contentType(MediaType.APPLICATION_JSON)
        		).andExpect(MockMvcResultMatchers.status().isOk());
		
    }
	
	@Test
	public void getNomineesByYear_ReturnStatus400() throws Exception {
		
		setUp();
		
		String year = "no-number";
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/" + ConstantsURL.URL_NOMINEES + "/"+ year)
        		.contentType(MediaType.APPLICATION_JSON)
        		).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
    }
	
	@Test
	public void getNomineesByYear_ReturnStatus200() throws Exception {
		
		setUp();
		
		String year = "1980";
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/" + ConstantsURL.URL_NOMINEES + "/"+ year)
        		.contentType(MediaType.APPLICATION_JSON)
        		).andExpect(MockMvcResultMatchers.status().isOk());
		
    }
	
	@Test
	public void getWinnersByYear_ReturnStatus400() throws Exception {
		
		setUp();
		
		String year = "no-number";
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/" + ConstantsURL.URL_WINNERS + "/"+ year)
        		.contentType(MediaType.APPLICATION_JSON)
        		).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}
