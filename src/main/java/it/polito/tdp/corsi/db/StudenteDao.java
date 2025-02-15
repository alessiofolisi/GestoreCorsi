package it.polito.tdp.corsi.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.corsi.model.Divisione;
import it.polito.tdp.corsi.model.Studente;

public class StudenteDao {
	
	
	public List<Studente> getStudentiByCorso(String corso) {
		
		String sql = "select s.matricola, s.cognome, s.nome, s.CDS "
				+ "from studente s , iscrizione i "
				+ "where s.matricola = i.matricola AND i.codins = ?" ;
		
		List<Studente> result = new ArrayList<Studente>();
		
		try {
			
			Connection conn = connectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(new Studente(rs.getInt("matricola") , rs.getString("cognome") , rs.getString("nome") , rs.getString("CDS")));
			}
			
			rs.close();
			st.close();
			conn.close();
			
		} catch(SQLException e) {
			System.err.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}	
		return result;
	}
        
	public List<Divisione> getDivisioneStudenti (String codins){
		
		String sql = "select s.CDS, COUNT(*) as n "
				+ "from studente s , iscrizione i "
				+ "where s.matricola = i.matricola AND i.codins = ? AND s.CDS <> '' "
				+ "group by s.CDS";
		List<Divisione> result = new ArrayList<Divisione>();
		
		try {
			
			Connection conn = connectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(new Divisione( rs.getString("CDS") , rs.getInt("n")));
			}
			
			
		} catch(SQLException e) {
			System.err.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	

}
