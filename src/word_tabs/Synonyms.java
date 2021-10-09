package word_tabs;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Synonyms {
	
	 
	static ArrayList<HashMap<String, Object>> all_sys = new ArrayList<HashMap<String, Object>>();
	static HashMap<String, Object> hash_syn = new HashMap<String, Object>();

	static ArrayList<String> temp_arr = new ArrayList<String>();

	public static ArrayList<HashMap<String, Object>> synonyms_data(String word) throws Exception {

		ArrayList<HashMap<String, Object>> all_syns = new ArrayList<HashMap<String, Object>>();
		ArrayList<String> data = new ArrayList<String>();
		HashMap<String, Object> hash = new HashMap<String, Object>();

		String api_data = data_retrieve.FrazeRetrieve.getAPIdata(word, "syn");

		if (api_data.startsWith("<")) {

			Document doc = data_retrieve.FrazeRetrieve.convertStringToXMLDocument(api_data);

			NodeList nList = doc.getElementsByTagName("entry");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					NodeList synonym = eElement.getElementsByTagName("synonym");
					Node type = eElement.getElementsByTagName("type").item(0);

					hash.put("type", type.getTextContent());

					for (int count = 0; count < synonym.getLength(); count++) {
						Node node1 = synonym.item(count);

						if (node1.getNodeType() == node1.ELEMENT_NODE) {
							Element e = (Element) node1;

							data.add(e.getTextContent());

						}
					}

					hash.put("syns", data.clone());

				}

				all_syns.add((HashMap<String, Object>) hash.clone());
				data.clear();
				hash.clear();

			}

		}

		else {
			JSONObject jsonObj = new JSONObject(api_data.toString());
			
			
			all_sys.clear();
			all_syns = get_defs_data(jsonObj);

		}

		return all_syns;

	}
	
	
	public static ArrayList<HashMap<String, Object>> get_defs_data(JSONObject json) {

		JSONArray keys = json.names();

		for (int i = 0; i < keys.length(); i++) {

			String key = keys.getString(i); // Here's your key
			Object keyvalue = json.get(key); // Here's your value

			if (keyvalue instanceof JSONArray) {

				if (key.equals("synonyms")) {

					 

					for (int j = 0; j < ((JSONArray) keyvalue).length(); j++) {
						temp_arr.add(((JSONArray) keyvalue).get(j).toString());

					}

					hash_syn.put("syns", temp_arr.clone());

				}

				else {

					// System.out.println(key + "---" + "array");
					for (int j = 0; j < ((JSONArray) keyvalue).length(); j++) {
						get_defs_data(((JSONArray) keyvalue).getJSONObject(j));
					}

				}

			}

			else if (keyvalue instanceof JSONObject) {

				 
				get_defs_data((JSONObject) keyvalue);
			}

			else {

				// -------values------
				if (key.equals("type")) {

					hash_syn.put("type", keyvalue);
					all_sys.add((HashMap<String, Object>) hash_syn.clone());
					temp_arr.clear();
					hash_syn.clear();
				}

				 

			}
		}

		return all_sys;

	}

}
