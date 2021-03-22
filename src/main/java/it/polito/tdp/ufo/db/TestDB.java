package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestDB {

	public static void main(String[] args) {
		
		String jdbcURL = "jdbc:mysql://localhost/ufo_sightings?user=root&password=root";
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			
			String sql = "SELECT DISTINCT shape " //ricordarsi di mettere spazio dopo ogni riga (tranne ultima)
					+ "FROM sighting "
					+ "WHERE shape<>'' "
					+ "ORDER BY shape ASC" ;
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			
			ResultSet res = st.executeQuery(); // non faccio new perchè fa factoring!
			
			List<String> formeUFO = new ArrayList<>();
			while(res.next()) {
				String forma = res.getString("shape"); // da qui db ha finito suo lavoro, il resto del prog lavora
				formeUFO.add(forma);
			}
			st.close(); // rilascio le risorse, è più impo la chiusura della conn
			
			System.out.println(formeUFO);
			
			String sql2 = "SELECT COUNT(*) AS cnt FROM sighting WHERE shape =?";
			String shapeScelta = "circle";
			
			PreparedStatement st2 = conn.prepareStatement(sql2);
			st2.setString(1, shapeScelta); // 1 perchè è il primo parametro (e anche l'ultimo)
			ResultSet res2 = st2.executeQuery();
			res2.first();
			int count = res2.getInt("cnt");
			
			st2.close();
			
			System.out.println("UFO di forma " + shapeScelta + " sono: " + count);
			
			conn.close(); // il db accetta un num limitato di connessioni massime, se non le chiudo posso avere conn negate
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
