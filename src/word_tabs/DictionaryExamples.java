package word_tabs;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;


public class DictionaryExamples {

	static ArrayList<HashMap<String, Object>> all_examples = new ArrayList<HashMap<String, Object>>();
	static HashMap<String, Object> hash_def = new HashMap<String, Object>();

	static ArrayList<String> examples = new ArrayList<String>();

	static boolean isExample = false;

	static boolean isNewType = false;
	static boolean isType = false;

	public static ArrayList<HashMap<String, Object>> getDicExamples(JSONObject json) {

		all_examples.clear();
		examples.clear();
		isExample = false;
		isNewType = false;
		isType = false;

		makeDicExamples(json);

 
		return all_examples;

	}

	public static void makeDicExamples(JSONObject json) {

		if (json != null) {

			JSONArray keys = json.names();

			for (int i = 0; i < keys.length(); i++) {

				String key = keys.getString(i); // Here's your key
				Object keyvalue = json.get(key); // Here's your value

				if (keyvalue instanceof JSONArray) {

					if (!(key.equals("dialects")) && !(key.equals("senseIds")) && !(key.equals("crossReferenceMarkers"))
							&& !(key.equals("definitions")) && !(key.equals("regions"))) {

						//System.out.println(key + "---" + "array" + " --------------------- ");

						if (key.equals("sentences")) {
							isExample = true;
						}

						for (int j = 0; j < ((JSONArray) keyvalue).length(); j++) {
							makeDicExamples(((JSONArray) keyvalue).getJSONObject(j));
						}

						if (key.equals("sentences")) {
							isExample = false;
						}

						// The end of one result and start new one
						else if (key.equals("lexicalEntries")) {
							all_examples.add((HashMap<String, Object>) hash_def.clone());
							hash_def.clear();
							examples.clear();
						}
					}

				}

				else if (keyvalue instanceof JSONObject) {

					if (key.equals("lexicalCategory")) {
						isType = true;
					}

					//System.out.println(key + "---" + "object" + " --------------------- " + keyvalue);
					makeDicExamples((JSONObject) keyvalue);

					if (key.equals("lexicalCategory")) {
						isType = false;
					}
				}

				else {

					// -------values------

					if (key.equals("text") && isExample) {
						examples.add(keyvalue.toString());
					}

					else if (key.equals("text") && isType) {
						hash_def.put("type", keyvalue);
						hash_def.put("examples", examples.clone());
					}

					//System.out.println(key + "---" + "value" + " --------------------- " + keyvalue);

				}

			}
		}

	}

}