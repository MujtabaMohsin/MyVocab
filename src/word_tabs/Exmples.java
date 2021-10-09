package word_tabs;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Exmples {
	
	static ArrayList<String> all_examples = new ArrayList<String>();
	
	public static ArrayList<String> examples_data(String word) throws Exception {
		all_examples.clear();
		
		ArrayList<String> data = new ArrayList<String>();
		
		String api_data = data_retrieve.FrazeRetrieve.getAPIdata(word, "phrase");

		// if XML file is retrieved
		if (api_data.startsWith("<")) {

			Document doc = data_retrieve.FrazeRetrieve.convertStringToXMLDocument(api_data);

			NodeList nList = doc.getElementsByTagName("phrase");
			for (int i = 0; i < nList.getLength(); i++) {

				data.add(nList.item(i).getTextContent());

			}

		}

		else if (api_data.startsWith("{")) { 
			
			JSONObject jsonObj = new JSONObject(api_data.toString());
			all_examples.clear();
			data = getExamplesJSON(jsonObj);
		}

		return data;

	}

	public static ArrayList<String> getExamplesJSON(JSONObject json) {

		JSONArray keys = json.names();

		for (int i = 0; i < keys.length(); i++) {

			String key = keys.getString(i); // Here's your key
			Object keyvalue = json.get(key); // Here's your value

			if (keyvalue instanceof JSONArray) {

				// System.out.println(key + "---" + "array");

				for (int j = 0; j < ((JSONArray) keyvalue).length(); j++) {
					getExamplesJSON(((JSONArray) keyvalue).getJSONObject(j));

				}

			}

			else if (keyvalue instanceof JSONObject) {

				// System.out.println(key + "---" + "object");
				getExamplesJSON((JSONObject) keyvalue);
			}

			else {

				// -------values------
				if (key.equals("phrase")) {
					all_examples.add(keyvalue.toString());
				}

				// System.out.println(key + "---" + "value");

			}

		}

		return all_examples;

	}

}
