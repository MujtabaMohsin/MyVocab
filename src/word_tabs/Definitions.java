package word_tabs;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Definitions {

	static ArrayList<HashMap<String, Object>> all_definitions = new ArrayList<HashMap<String, Object>>();
	static HashMap<String, Object> hash_def = new HashMap<String, Object>();

	static ArrayList<String> temp_arr_def = new ArrayList<String>();

	public static ArrayList<HashMap<String, Object>> definitions_data(String word) throws Exception {

		all_definitions.clear();
		String api_data = data_retrieve.FrazeRetrieve.getAPIdata(word, "dico");

		if (api_data.startsWith("<")) {

			Document doc = data_retrieve.FrazeRetrieve.convertStringToXMLDocument(api_data);

			NodeList nList = doc.getElementsByTagName("entry");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					NodeList synonym = eElement.getElementsByTagName("definition");
					Node type = eElement.getElementsByTagName("type").item(0);

					hash_def.put("type", type.getTextContent());

					for (int count = 0; count < synonym.getLength(); count++) {
						Node node1 = synonym.item(count);

						if (node1.getNodeType() == node1.ELEMENT_NODE) {
							Element e = (Element) node1;

							temp_arr_def.add(e.getTextContent());

						}
					}

					hash_def.put("defs", temp_arr_def.clone());

				}

				all_definitions.add((HashMap<String, Object>) hash_def.clone());
				temp_arr_def.clear();
				hash_def.clear();

			}

		}

		else {
			all_definitions.clear();
			JSONObject json = new JSONObject(api_data);
			return get_defs_data(json);

		}
		return all_definitions;
	}

	public static ArrayList<HashMap<String, Object>> get_defs_data(JSONObject json) {

		JSONArray keys = json.names();

		for (int i = 0; i < keys.length(); i++) {

			String key = keys.getString(i); // Here's your key
			Object keyvalue = json.get(key); // Here's your value

			if (keyvalue instanceof JSONArray) {

				if (key.equals("definitions")) {

					// System.out.println(key + "---" + "array");

					for (int j = 0; j < ((JSONArray) keyvalue).length(); j++) {
						temp_arr_def.add(((JSONArray) keyvalue).get(j).toString());

					}

					hash_def.put("defs", temp_arr_def.clone());
					all_definitions.add((HashMap<String, Object>) hash_def.clone());

				}

				else {

					// System.out.println(key + "---" + "array");
					for (int j = 0; j < ((JSONArray) keyvalue).length(); j++) {
						get_defs_data(((JSONArray) keyvalue).getJSONObject(j));
					}

				}

			}

			else if (keyvalue instanceof JSONObject) {

				// System.out.println(key + "---" + "obj");
				get_defs_data((JSONObject) keyvalue);
			}

			else {

				// -------values------
				if (key.equals("type")) {
					temp_arr_def.clear();
					hash_def.clear();
					hash_def.put("type", keyvalue);
				}

				// System.out.println(key + "---" + "value");

			}
		}

		return all_definitions;

	}

}
