package com.goldenraspberryawards.apirest.resources;

import java.io.IOException;
import java.util.List;

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

import com.goldenraspberryawards.apirest.dto.IntervalAwardsDTO;
import com.goldenraspberryawards.apirest.dto.NomineesAwardsDTO;
import com.goldenraspberryawards.apirest.dto.PathFileDTO;
import com.goldenraspberryawards.apirest.services.MovieAwardsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API Rest Golden Raspeberry Awards")
public class MovieAwardsResource {
	
	
	@Autowired
	MovieAwardsService movieAwardsService;
	
	
	@ApiOperation(value="Retorna o intervalo min e max de vencedores")
	@GetMapping("/intervalo-premios")
	public IntervalAwardsDTO getMinMaxIntervalAwards(){
		return movieAwardsService.getListIntervalAwards();
	}
	
	
	@ApiOperation(value="Importa um arquivo csv via path. O arquivo deve ter os seguintes campos na ordem: year, title, studios, producers, winner")
	@PostMapping("importar-arquivo-path")
	public ResponseEntity<String> importNomineesByPath(@RequestBody PathFileDTO path) {
		
		try {
			movieAwardsService.importNomineesByPath(path.getPathFile());
			
			return new ResponseEntity<String>("Importado com sucesso", HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value="Importa um arquivo csv via file. O arquivo deve ter os seguintes campos na ordem: year, title, studios, producers, winner")
	@PostMapping("importar-arquivo-file")
	public ResponseEntity<String> importNomineesByFile(@RequestParam("file") MultipartFile file) throws IOException {
		try {
			
			movieAwardsService.importNomineesByFile(file);
			
			return new ResponseEntity<String>("Importado com sucesso", HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Lista todos os vencedores")
	@GetMapping("/vencedores")
	public List<NomineesAwardsDTO> getWinners(){
		return movieAwardsService.listAllWinners();
	}
	
	@ApiOperation(value="Lista os vencedores de um ano")
	@GetMapping("/vencedores/{year}")
	public List<NomineesAwardsDTO> getWinnersByYear(@PathVariable(value="year") Integer year){
		return movieAwardsService.findWinnersByYear(year);
	}
	
	@ApiOperation(value="Lista as nomeações de um ano")
	@GetMapping("/nomeacoes/{year}")
	public List<NomineesAwardsDTO> getNomineesByYear(@PathVariable(value="year") Integer year){
		return movieAwardsService.listNomineesByYear(year);
	}
}
