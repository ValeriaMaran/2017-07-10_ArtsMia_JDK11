package it.polito.tdp.artsmia.model;

import java.util.Map;
import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private SimpleWeightedGraph<ArtObject, DefaultWeightedEdge> grafo;
	Map<Integer,ArtObject> idMap;
	ArtsmiaDAO dao;
	public Model() {
		dao = new ArtsmiaDAO();
	}
	
	public void CreaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<Integer,ArtObject>();
		List<Adiacenze> adiacenzeModel;
		
		dao.listObjects(idMap);
		
		for(ArtObject ao : idMap.values()) {
			grafo.addVertex(ao);
		}
		
		adiacenzeModel = dao.getAdiacenze(idMap);
				
		for(Adiacenze a : adiacenzeModel) {
			Graphs.addEdgeWithVertices(grafo,a.getA1(),a.getA2(),a.getPeso());
		}
	}
	
	public int getNumeroVertici() {
		return grafo.vertexSet().size();
		
	}
	public int getNumeroArchi() {
		return grafo.edgeSet().size();
	}
	
	public boolean verificaCodiceObject(Integer idObject) {
		if(idMap.containsKey(idObject)==true) {
			return true;
		}
		else 
			return false;
	}
	
	public int calcolaComponenteConnessa(Integer objID) {
		List<ArtObject> componentiConnesse = new LinkedList<ArtObject>();
		dao.listObjects(idMap);
		ArtObject ao =null;
		if(verificaCodiceObject(objID) ==true) {
			if(idMap.containsKey(objID)==true)
				ao = idMap.get(objID);
		}
		if(ao!=null) {
			DepthFirstIterator<ArtObject,DefaultWeightedEdge> it = new DepthFirstIterator<>(this.grafo,ao);
			while(it.hasNext()) {
				ArtObject temp = it.next();
				componentiConnesse.add(temp);
			}
		}
		return componentiConnesse.size();
		
		
	}
}
