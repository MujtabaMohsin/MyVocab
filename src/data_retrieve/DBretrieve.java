
package data_retrieve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DBretrieve {

	static Connection con;

	public static void getConnction() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con = DriverManager.getConnection("jdbc:mysql://localhost:3307/myvocab", "root", "");

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

	public static ArrayList<String> getMyExamples(String word) throws SQLException {

		ArrayList<String> examples = new ArrayList<String>();

		getConnction();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT text FROM myexamples WHERE word = \'" + word + "\'");

		while (rs.next()) {

			examples.add(rs.getString(1));

		}

		con.close();

		return examples;

	}

	public static ArrayList<String> getMyTranslations(String word) throws SQLException {

		ArrayList<String> examples = new ArrayList<String>();

		getConnction();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT text FROM mytranslations WHERE word = \'" + word + "\'");

		while (rs.next()) {

			examples.add(rs.getString(1));

		}

		con.close();

		return examples;

	}

	public static void addNewExample(String word, String text) throws SQLException {

		getConnction();
		con.setAutoCommit(false);

		PreparedStatement pstmt = con.prepareStatement("INSERT INTO myexamples(word,text) VALUES(?,?)");

		pstmt.setString(1, word);
		pstmt.setString(2, text);
		pstmt.executeUpdate();
		con.commit();

		con.close();
	}

	public static void addNewTranslation(String word, String text) throws SQLException {

		getConnction();
		con.setAutoCommit(false);

		PreparedStatement pstmt = con.prepareStatement("INSERT INTO mytranslations(word,text) VALUES(?,?)");

		pstmt.setString(1, word);
		pstmt.setString(2, text);
		pstmt.executeUpdate();
		con.commit();

		con.close();
	}

	public static void addToMyList(String word,String list) throws SQLException {

		getConnction();
		con.setAutoCommit(false);

		PreparedStatement pstmt = con.prepareStatement("INSERT INTO words_list(word,list_id) VALUES(?,?)");

		pstmt.setString(1, word);
		pstmt.setString(2, list);

		pstmt.executeUpdate();
		con.commit();

		con.close();
	}

	public static void deleteFromMyList(String word, String list) throws SQLException {

		getConnction();

		PreparedStatement pstmt = con.prepareStatement("DELETE FROM words_list WHERE word = \'" + word + "\' AND list_id = \'"+list+"\'");

		pstmt.executeUpdate();

		con.close();
	}

	public static boolean checkWordInList(String word, String list) throws SQLException {

		ArrayList<String> examples = new ArrayList<String>();

		getConnction();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT id FROM words_list WHERE word = \'" + word + "\' AND list_id = \'"+list+"\'");

		int i = 0;
		while (rs.next()) {

			examples.add(rs.getString(1));
			i++;

		}

		con.close();

		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}

	public static void updateWordQuiz(String word, int result) throws SQLException {

		getConnction();
		con.setAutoCommit(false);

		PreparedStatement pstmt = con.prepareStatement(
				"UPDATE words_list SET last_quiz = ? , result = result + \'" + result + "\' WHERE word = ?");

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		pstmt.setTimestamp(1, timestamp);
		pstmt.setString(2, word);

		pstmt.executeUpdate();
		con.commit();

		con.close();
	}

	public static ArrayList<String> getQuizWords(String type, String list_id) throws SQLException {

		ArrayList<String> words = new ArrayList<String>();
		getConnction();
		Statement stmt = con.createStatement();

		if (type.equals("random")) {
			ResultSet rs = stmt.executeQuery("SELECT word FROM words_list WHERE list_id = " + list_id);

			while (rs.next()) {

				words.add(rs.getString(1));

			}
		}

		else if (type.equals("today")) {

			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String today = dateFormat.format(cal.getTime());

			cal.add(Calendar.DATE, -1);
			String yesterday = dateFormat.format(cal.getTime());

			ResultSet rs = stmt.executeQuery(
					"SELECT word FROM words_list WHERE list_id = \"" + list_id +"\" AND (date BETWEEN \"" + yesterday + "\" AND \"" + today + "\")");

			while (rs.next()) {

				words.add(rs.getString(1));

			}
		}

		else if (type.equals("three_days")) {

			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String today = dateFormat.format(cal.getTime());

			cal.add(Calendar.DATE, -3);
			String three_days = dateFormat.format(cal.getTime());

 
			ResultSet rs = stmt.executeQuery(
					"SELECT word FROM words_list WHERE list_id = \"" + list_id +"\" AND  (date BETWEEN \"" + three_days + "\" AND \"" + today + "\")");

			while (rs.next()) {

				words.add(rs.getString(1));

			}
		}

		else if (type.equals("this_week")) {

			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String today = dateFormat.format(cal.getTime());

			cal.add(Calendar.DATE, -7);
			String this_week = dateFormat.format(cal.getTime());

			ResultSet rs = stmt.executeQuery(
					"SELECT word FROM words_list WHERE list_id = \"" + list_id +"\" AND  (date BETWEEN \"" + this_week + "\" AND \"" + today + "\")");

			while (rs.next()) {

				words.add(rs.getString(1));

			}
		}

		else if (type.equals("fewest")) {

			ResultSet rs = stmt
					.executeQuery("SELECT word FROM words_list WHERE list_id = " + list_id + " ORDER BY result ASC");

			while (rs.next()) {

				words.add(rs.getString(1));

			}

		}

		else if (type.equals("never_tested")) {

			ResultSet rs = stmt
					.executeQuery("SELECT word FROM words_list WHERE list_id = " + list_id + " AND last_quiz IS NULL");

			while (rs.next()) {

				words.add(rs.getString(1));

			}

		}

		con.close();
		return words;

	}

	public static String getDefualtList() throws SQLException {

		String list_id = "";

		getConnction();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT def_list FROM settings");

		while (rs.next()) {

			list_id = rs.getString(1);

		}

		con.close();

		return list_id;

	}

	public static void updateSettings(String list_id) throws SQLException {

		getConnction();

		con.setAutoCommit(false);

		PreparedStatement pstmt = con.prepareStatement("UPDATE settings SET def_list = ?");

		pstmt.setString(1, list_id);

		pstmt.executeUpdate();
		con.commit();

		con.close();

	}
	
	
	public static ArrayList<String> getSpeakingQuestions() throws SQLException {

		ArrayList<String> questions = new ArrayList<String>();

		getConnction();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT question FROM speaking");

		while (rs.next()) {

			questions.add(rs.getString(1));

		}

		con.close();

		return questions;

	}

}
