package word_tabs;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Translations {
	

	static ArrayList<ArrayList<String>> all_translations = new ArrayList<ArrayList<String>>();

	static ArrayList<String> examples_en = new ArrayList<String>();
	static ArrayList<String> examples_ar = new ArrayList<String>();
	static ArrayList<String> translations = new ArrayList<String>();
	
	
	
	static boolean isExample = false;
	static String partent = "";
	static String pre_array = "";
	static String curr_array = "";
	
	
	public static ArrayList<ArrayList<String>> getTranslations(JSONObject json) {
		
		all_translations.clear();
		translations.clear();
		examples_ar.clear();
		examples_en.clear();
		isExample = false;
		
		makeTranslations(json);
		all_translations.add(translations);
		all_translations.add(examples_en);
		all_translations.add(examples_ar);
		
		return all_translations;
		
		
	}
	
	

	public static void makeTranslations(JSONObject json) {

		if (json != null) {

			JSONArray keys = json.names();

			for (int i = 0; i < keys.length(); i++) {

				String key = keys.getString(i); // Here's your key
				Object keyvalue = json.get(key); // Here's your value

				if (keyvalue instanceof JSONArray) {

					if (!(key.equals("dialects")) && !(key.equals("crossReferenceMarkers"))
							&& !(key.equals("definitions"))) {

						//System.out.println(key + "---" + "array" + " --------------------- " + keyvalue);
						
						 
						
						 
						
						if (key.equals("examples")) {
							isExample = true;
						}
						 
						 
						for (int j = 0; j < ((JSONArray) keyvalue).length(); j++) {
							makeTranslations(((JSONArray) keyvalue).getJSONObject(j));
						}
						
						if (key.equals("examples")) {
							isExample = false;
						}
					}

				}

				else if (keyvalue instanceof JSONObject) {

					//System.out.println(key + "---" + "object" + " --------------------- " + keyvalue);
					makeTranslations((JSONObject) keyvalue);
				}

				else {

					// -------values------
				 
					
					if (key.equals("text") && isExample && keyvalue.toString().matches("[\\p{Punct}\\p{Space}\\p{IsLatin}]+$") ) {
						examples_en.add(keyvalue.toString());
					}
					
					else if (key.equals("text") && isExample && !keyvalue.toString().matches("[\\p{Punct}\\p{Space}\\p{IsLatin}]+$")) {
						examples_ar.add(keyvalue.toString());
					}
					
					else if (key.equals("text") && !isExample && !keyvalue.toString().matches("[\\p{Punct}\\p{Space}\\p{IsLatin}]+$")) {
						translations.add(keyvalue.toString());
					}
					//System.out.println(key + "---" + "value" + " --------------------- " + keyvalue);

				}

			}
		}
		
		

	}

}
