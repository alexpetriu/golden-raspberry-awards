package com.goldenraspberryawards.apirest.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goldenraspberryawards.apirest.dto.IntervalAwardsDTO;
import com.goldenraspberryawards.apirest.dto.IntervalAwardsProducerDTO;
import com.goldenraspberryawards.apirest.dto.NomineesAwardsDTO;
import com.goldenraspberryawards.apirest.model.NomineesAwards;
import com.goldenraspberryawards.apirest.repository.NomineesAwardsRepository;
import com.goldenraspberryawards.apirest.validation.ValidationFileCsv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class MovieAwardsService {

	@Autowired
	NomineesAwardsRepository nomineesAwardsRepository;
	
	/*
	 * Importa os registros de um arquivo csv para a base de dados através de um path
	 * Os arquivos devem estar armazenados dentro de src/main/resources/csv-files
	 * O padrão do arquivo deverá ser: year, title, studios, producers, winner
	 * 
	 */
	public void importNomineesByPath(String path) throws Exception {
		Reader reader = Files.newBufferedReader(Paths.get(path));
		CSVReader csvReader = new CSVReader(reader, ';');
        
		List<String[]> lines = csvReader.readAll();
        
		reader.close();
		
		
		importNominees(lines);
	}
	
	/*
	 * Importa os registros de um arquivo csv para a base de dados através de upload de arquivo
	 * O padrão do arquivo deverá ser: year, title, studios, producers, winner
	 * 
	 */
	public void importNomineesByFile(MultipartFile file) throws Exception {
		
		BufferedReader br;
		
		List<String[]> lines = new ArrayList<>();
		
		String line;
	    
		InputStream is = file.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
		
		while ((line = br.readLine()) != null) {
	    	
			String[] split = line.split(";");
	    	
	    	if (split.length < 5) {
	    		
	    		String v0 = split[0];
	    		String v1 = split[1];
	    		String v2 = split[2];
	    		String v3 = split[3];
	    		String v4 = "";
	    		String[] split2 = {v0, v1, v2, v3, v4};
	    		lines.add(split2);
	    		
	    	} else {
	    		lines.add(split);
	    	}
	    	
	    }
	    
	    importNominees(lines);
	    
	}
	
	
	public void importNominees(List<String[]> lines) throws Exception {

		ValidationFileCsv validation = new ValidationFileCsv();
		
		lines = validation.validateData(lines);
		
		nomineesAwardsRepository.deleteAll();
		
		for (String[] line : lines) {
        	
        	Integer year = Integer.valueOf(line[0]);
        	String title = line[1];
        	String studios = line[2];
        	String producers = line[3];
        	boolean winner = (line[4]).toLowerCase().equals("yes");
        	
        	NomineesAwards nominees = new NomineesAwards(title, studios, producers, year, winner);
        	
        	nominees = nomineesAwardsRepository.save(nominees);
        
		}
		
	}
	
	/*
	 * Lista todas os nomeados
	 * 
	 */
	public List<NomineesAwards> listNomineesAwards() {
		return nomineesAwardsRepository.findAll();
	}
	
	/*
	 * Encontra os vencedores do ano
	 */
	public List<NomineesAwardsDTO> findWinnersByYear(Integer year) {
		
		List<NomineesAwards> listEntities = nomineesAwardsRepository.findWinnersByYear(year);
		List<NomineesAwardsDTO> listDtos = new ArrayList<>();
		
		listEntities.stream().forEach((nominees) -> {
			NomineesAwardsDTO nomineesDto = new NomineesAwardsDTO();
			BeanUtils.copyProperties(nominees, nomineesDto);
			listDtos.add(nomineesDto);
		});
		
		return listDtos;
	}
	
	/*
	 * Lista todos os ganhadores
	 */
	public List<NomineesAwardsDTO> listAllWinners() {
		
		List<NomineesAwards> listEntities = nomineesAwardsRepository.findAllWinners();
		List<NomineesAwardsDTO> listDtos = new ArrayList<>();
		
		listEntities.stream().forEach((nominees) -> {
			NomineesAwardsDTO nomineesDto = new NomineesAwardsDTO();
			BeanUtils.copyProperties(nominees, nomineesDto);
			listDtos.add(nomineesDto);
		});
		
		return listDtos;
	}
	
	/*
	 * Lista todos os nomeados do ano
	 */
	public List<NomineesAwardsDTO> listNomineesByYear(Integer year) {
		
		List<NomineesAwards> listEntities = nomineesAwardsRepository.findNomineesByYear(year);
		List<NomineesAwardsDTO> listDtos = new ArrayList<>();
		
		listEntities.stream().forEach((nominees) -> {
			NomineesAwardsDTO nomineesDto = new NomineesAwardsDTO();
			BeanUtils.copyProperties(nominees, nomineesDto);
			listDtos.add(nomineesDto);
		});
		
		return listDtos;
	}
	
	/*
	 * Método responsável por retornar um mapa de Produtores com seus respectivos anos premiados
	 */
	public Map<String, List<Integer>> getMapIntervals(List<NomineesAwards> winners) {
		Map<String, List<Integer>> mapWinners = new HashMap<>();
		
		winners.stream().forEach((win) -> {
			if (!mapWinners.containsKey(win.getProducer())) {
				mapWinners.put(win.getProducer(), new ArrayList<>());
			}
			List<Integer> years = mapWinners.get(win.getProducer());
			years.add(win.getYear());
			mapWinners.put(win.getProducer(), years);
		});
		
		return mapWinners;
	}
	
	public List<IntervalAwardsProducerDTO> getListIntervalsAwardsProducers(Map<String, List<Integer>> mapWinners) {
		
		List<IntervalAwardsProducerDTO> listAwardsProducers = new ArrayList<>();
		
		for (Entry<String, List<Integer>> entry : mapWinners.entrySet()) {
			List<Integer> years = entry.getValue();
			if (years != null && !years.isEmpty() && years.size() > 1) {
				
				Collections.sort(years);
				
				Integer lastYear = years.get(0);
				
				for (Integer year : years) {
					
					IntervalAwardsProducerDTO intervalProducer = new IntervalAwardsProducerDTO();
					intervalProducer.setProducer(entry.getKey());
					
					if (year.equals(years.get(0))) {
						continue;
					}
					intervalProducer.setPreviousWin(lastYear);
					intervalProducer.setFollowingWin(year);
					intervalProducer.setInterval(year - lastYear);
					
					listAwardsProducers.add(intervalProducer);
					
					lastYear = year;
					
				}
			}
		}
		
		return listAwardsProducers;
	}
	
	public List<IntervalAwardsProducerDTO> getListMinOrMaxIntervals(List<IntervalAwardsProducerDTO> listIntervalsProducers, Comparator<IntervalAwardsProducerDTO> compareByInterval, boolean min) {
		
		List<IntervalAwardsProducerDTO> minMaxIntervalsProducers = new ArrayList<>();
		
		Collections.sort(listIntervalsProducers, compareByInterval);
		
		Integer lastInterval = 0;
		
		for (IntervalAwardsProducerDTO producer : listIntervalsProducers) {
			if (lastInterval == 0
					|| (producer.getInterval() <= lastInterval && min)
					|| (producer.getInterval() >= lastInterval && !min)) {
				minMaxIntervalsProducers.add(producer);
				lastInterval = producer.getInterval();
			}
		}
		
		return minMaxIntervalsProducers;
		
	}
	
	
	public IntervalAwardsDTO getMinMaxIntervals(List<IntervalAwardsProducerDTO> listIntervalsProducers) {
		
		if (listIntervalsProducers == null || listIntervalsProducers.isEmpty()) {
			return null;
		}
		
		Comparator<IntervalAwardsProducerDTO> compareByInterval = 
				(IntervalAwardsProducerDTO o1, IntervalAwardsProducerDTO o2) -> o1.getInterval().compareTo( o2.getInterval() );
		
		
		IntervalAwardsDTO interval = new IntervalAwardsDTO();
		
		interval.setMin(getListMinOrMaxIntervals(listIntervalsProducers, compareByInterval, true));
		interval.setMax(getListMinOrMaxIntervals(listIntervalsProducers, compareByInterval.reversed(), false));
				
		return interval;
		
	}
	
	/*
	 * Método responsável por retornar os vencedores que teve um menor e o maior intervalo consecutivo de anos
	 */
	public IntervalAwardsDTO getListIntervalAwards() {
		
		List<NomineesAwards> winners = nomineesAwardsRepository.findAllWinners();
		
		//gera um mapa de produtors e com seus respectivos anos premiados
		Map<String, List<Integer>> mapWinners = getMapIntervals(winners);
		
		//gera uma lista de dto com os intervalos e anos previous e following apartir do Map
		List<IntervalAwardsProducerDTO> listIntervalsProducers = getListIntervalsAwardsProducers(mapWinners);
		
		//pega os minimos e maximos dos intervalos da lista
		IntervalAwardsDTO intervalAwards = getMinMaxIntervals(listIntervalsProducers);
		
		return intervalAwards;
	}
}
