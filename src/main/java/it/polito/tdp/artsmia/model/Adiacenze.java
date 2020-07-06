package it.polito.tdp.artsmia.model;

import java.util.LinkedList;
import java.util.List;

public class Adiacenze {
	private ArtObject a1;
	private ArtObject a2;
	private Integer peso;
	
	private List<Adiacenze> adiacenze;
	public Adiacenze(ArtObject a1, ArtObject a2, Integer peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
		adiacenze = new LinkedList<Adiacenze>();
	}
	public ArtObject getA1() {
		return a1;
	}
	public void setA1(ArtObject a1) {
		this.a1 = a1;
	}
	public ArtObject getA2() {
		return a2;
	}
	public void setA2(ArtObject a2) {
		this.a2 = a2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	public List<Adiacenze> getAdiacenzeList(){
		return this.adiacenze;
	}
}
