package com.goldenraspberryawards.apirest.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_nominees_awards")
public class NomineesAwards implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String studio;
	
	private String producer;
	
	private Integer year;
	
	private boolean winner;
	
	public NomineesAwards(String title, String studio, String producer, Integer year, boolean winner) {
		this.title = title;
		this.studio = studio;
		this.producer = producer;
		this.year = year;
		this.winner = winner;
	}
	
	public NomineesAwards() {
		//default constructor
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, producer, studio, title, winner, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NomineesAwards other = (NomineesAwards) obj;
		return Objects.equals(id, other.id) && Objects.equals(producer, other.producer)
				&& Objects.equals(studio, other.studio) && Objects.equals(title, other.title) && winner == other.winner
				&& Objects.equals(year, other.year);
	}
	
	
	
}
	