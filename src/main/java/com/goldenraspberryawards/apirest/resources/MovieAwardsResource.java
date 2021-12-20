package com.goldenraspberryawards.apirest.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

import com.goldenraspberryawards.apirest.constants.ConstantsURL;
import com.goldenraspberryawards.apirest.dto.IntervalAwardsDTO;
import com.goldenraspberryawards.apirest.dto.NomineesAwardsDTO;
import com.goldenraspberryawards.apirest.dto.PathFileDTO;
import com.goldenraspberryawards.apirest.services.MovieAwardsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api/")
@Api(value="API Rest Golden Raspeberry Awards")
public class MovieAwardsResource {
	
	
	@Autowired
	MovieAwardsService movieAwardsService;
	
	
	@ApiOperation(value="Retorna o intervalo min e max de vencedores")
	@GetMapping(ConstantsURL.URL_INTERVAL_WINS)
	public IntervalAwardsDTO getMinMaxIntervalAwards(){
		return movieAwardsService.getListIntervalAwards();
	}
	
	
	@ApiOperation(value="Importa um arquivo csv via path. Local dos arquivos: 'src/main/resources/csv-files' O arquivo deve ter os seguintes campos na ordem: year, title, studios, producers, winner")
	@PostMapping(ConstantsURL.URL_IMPORT_PATH)
	public ResponseEntity<Map<String, String>> importNomineesByPath(@RequestBody PathFileDTO path) {
		
		Map<String, String> result = new HashMap<>();
		
		try {
			movieAwardsService.importNomineesByPath(path.getPathFile());
			
			result.put("status", "OK");
			result.put("message", "Importado com sucesso");
			
			return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			
			result.put("status", "Error");
			result.put("message", ex.getMessage());
			
			return new ResponseEntity<Map<String, String>>(result, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value="Importa um arquivo csv via file. O arquivo deve ter os seguintes campos na ordem: year, title, studios, producers, winner")
	@PostMapping(ConstantsURL.URL_IMPORT_FILE)
	public ResponseEntity<Map<String, String>> importNomineesByFile(@RequestParam("file") MultipartFile file) {
		
		Map<String, String> result = new HashMap<>();
		
		try {
			
			movieAwardsService.importNomineesByFile(file);
			
			result.put("status", "OK");
			result.put("message", "Importado com sucesso");
			
			return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			result.put("status", "Error");
			result.put("message", ex.getMessage());
			
			return new ResponseEntity<Map<String, String>>(result, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Lista todos os vencedores")
	@GetMapping(ConstantsURL.URL_WINNERS)
	public List<NomineesAwardsDTO> getWinners(){
		return movieAwardsService.listAllWinners();
	}
	
	@ApiOperation(value="Lista os vencedores de um ano")
	@GetMapping(ConstantsURL.URL_WINNERS + "/{year}")
	public List<NomineesAwardsDTO> getWinnersByYear(@PathVariable(value="year") Integer year){
		return movieAwardsService.findWinnersByYear(year);
	}
	
	@ApiOperation(value="Lista as nomeações de um ano")
	@GetMapping(ConstantsURL.URL_NOMINEES + "/{year}")
	public List<NomineesAwardsDTO> getNomineesByYear(@PathVariable(value="year") Integer year){
		return movieAwardsService.listNomineesByYear(year);
	}
}
