package com.goldenraspberryawards.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.goldenraspberryawards.apirest.model.NomineesAwards;


public interface NomineesAwardsRepository extends JpaRepository<NomineesAwards, Long>{
	
	@Query("select na from NomineesAwards na where winner = true order by year asc")
	List<NomineesAwards> findAllWinners();
	
	@Query("select na from NomineesAwards na where winner = true and year = ?1 order by year asc")
	List<NomineesAwards> findWinnersByYear(Integer year);
	
	@Query("select na from NomineesAwards na where year = ?1 order by year asc")
	List<NomineesAwards> findNomineesByYear(Integer year);
}
