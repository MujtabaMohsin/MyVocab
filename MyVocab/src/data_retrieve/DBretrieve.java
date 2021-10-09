
package data_retrieve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBretrieve {

	static Connection con;

	public static void getConnction() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myvocab", "root", "");

	}

	public static ArrayList<ArrayList<String>> getMyWordList(String list_id) throws SQLException {

		ArrayList<ArrayList<String>> MylistData = new ArrayList<ArrayList<String>>();
		ArrayList<String> ids = new ArrayList<String>();
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<String> results = new ArrayList<String>();
		getConnction();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT id,word,result FROM words_list WHERE list_id = " + list_id);

		while (rs.next()) {

			ids.add(rs.getString(1));
			words.add(rs.getString(2));
			results.add(rs.getString(3));

		}

		MylistData.add(ids);
		MylistData.add(words);
		MylistData.add(results);
		con.close();

		return MylistData;

	}

	public static ArrayList<ArrayList<String>> getMyLists() throws SQLException {

		ArrayList<ArrayList<String>> all_lists = new ArrayList<ArrayList<String>>();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> ids = new ArrayList<String>();

		getConnction();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT id,name  FROM mylists");

		while (rs.next()) {

			names.add(rs.getString(1));
			ids.add(rs.getString(2));

		}

		all_lists.add(names);
		all_lists.add(ids);
		con.close();

		return all_lists;

	}

	public static void addNewList(String name) throws SQLException {

		getConnction();
		con.setAutoCommit(false);

		PreparedStatement pstmt = con.prepareStatement("INSERT INTO mylists(name) VALUES(?)");

		pstmt.setString(1, name);
		pstmt.executeUpdate();
		con.commit();

		con.close();
	}

}
