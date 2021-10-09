package word_tabs;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Dictionary {
	
	
	static ArrayList<HashMap<String, Object>> all_definitions = new ArrayList<HashMap<String, Object>>();
	static HashMap<String, Object> hash_def = new HashMap<String, Object>();

	static ArrayList<String> temp_arr_def = new ArrayList<String>();
	 

	public static ArrayList<HashMap<String, Object>> getDictionary(JSONObject json) {

//		makeDictionary(json);
//		all_translations.add(translations);
//		all_translations.add(examples_en);
//		all_translations.add(examples_ar);

		return null;

	}

	public static void makeDictionary(JSONObject json) {

//		if (json != null) {
//
//			JSONArray keys = json.names();
//
//			for (int i = 0; i < keys.length(); i++) {
//
//				String key = keys.getString(i); // Here's your key
//				Object keyvalue = json.get(key); // Here's your value
//
//				if (keyvalue instanceof JSONArray) {
//
//					if (!(key.equals("dialects")) && !(key.equals("crossReferenceMarkers"))
//							&& !(key.equals("definitions"))) {
//
//						System.out.println(key + "---" + "array" + " --------------------- " +
//						 keyvalue);
//
//						if (key.equals("examples")) {
//							//isExample = true;
//						}
//
//						for (int j = 0; j < ((JSONArray) keyvalue).length(); j++) {
//							makeDictionary(((JSONArray) keyvalue).getJSONObject(j));
//						}
//
//						if (key.equals("examples")) {
//							//isExample = false;
//						}
//					}
//
//				}
//
//				else if (keyvalue instanceof JSONObject) {
//
//					System.out.println(key + "---" + "object" + " --------------------- " +
//					keyvalue);
//					makeDictionary((JSONObject) keyvalue);
//				}
//
//				else {
//
//					// -------values------
//
//					
//					System.out.println(key + "---" + "value" + " --------------------- " +
//					 keyvalue);
//
//				}
//
//			}
//		}
//
//	}

}}
