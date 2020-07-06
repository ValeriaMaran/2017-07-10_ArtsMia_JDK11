package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.Adiacenze;
import it.polito.tdp.artsmia.model.ArtObject;
import java.util.*;
public class ArtsmiaDAO {

	public Map<Integer,ArtObject> listObjects(Map<Integer,ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				if(idMap.containsKey(res.getInt("object_id"))==false) {
					idMap.put(artObj.getId(),artObj);
				}
				
			}
			conn.close();
			return idMap;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenze> getAdiacenze(Map<Integer,ArtObject> idMap){
		List<Adiacenze> adiacenze = new LinkedList<Adiacenze>();
		String sql = "SELECT eo1.object_id,eo2.object_id, COUNT(DISTINCT (eo1.exhibition_id)) AS peso "
				+ "FROM exhibition_objects eo1, exhibition_objects eo2 "
				+ "WHERE eo1.object_id>eo2.object_id AND eo1.exhibition_id=eo2.exhibition_id "
				+ "GROUP BY eo1.object_id, eo2.object_id";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				if(idMap.containsKey(rs.getInt("eo1.object_id"))==true && idMap.containsKey(rs.getInt("eo2.object_id"))) {
					ArtObject a1 = idMap.get(rs.getInt("eo1.object_id"));
					ArtObject a2 = idMap.get(rs.getInt("eo2.object_id"));
					
					Adiacenze a = new Adiacenze(a1,a2,rs.getInt("peso"));
					adiacenze.add(a);
				}
			}
			conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return adiacenze;
	}
	
}
