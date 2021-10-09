package data_retrieve;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FrazeRetrieve {

	final static String key = "c3252735-66bd-4573-82e0-528f8348284a";

	static ArrayList<HashMap<String, Object>> all_definitions = new ArrayList<HashMap<String, Object>>();
	static HashMap<String, Object> hash_def = new HashMap<String, Object>();

	static ArrayList<String> temp_arr_def = new ArrayList<String>();

	public static String getXMLdata(String word, String type) throws Exception {

		String uri = "";

		// types = syn || dico || phrase || sug
		switch (type) {
		case "phrase":
			uri = "https://fraze.it/api/" + type + "/" + word + "/en/1/no/" + key;
			break;

		case "dico":
			uri = "https://fraze.it/api/" + type + "/" + word + "/en/" + key;
			break;

		case "syn":
			uri = "https://fraze.it/api/" + type + "/" + word + "/en/" + key;
			break;

		case "sug":
			uri = "https://fraze.it/api/" + type + "/" + word + "/en/10/" + key;
			break;

		default:
			break;
		}

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		// XML data
		return response.body();
	}

	private static Document convertStringToXMLDocument(String xmlString) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> examples_data(String word) throws Exception {

		ArrayList<String> data = new ArrayList<String>();
		String xml = getXMLdata(word, "phrase");

		Document doc = convertStringToXMLDocument(xml);

		NodeList nList = doc.getElementsByTagName("phrase");

		for (int i = 0; i < nList.getLength(); i++) {

			data.add(nList.item(i).getTextContent());

		}

		return data;

	}

	public static ArrayList<String> forms_data(String word) throws Exception {

		ArrayList<String> data = new ArrayList<String>();
		String xml = getXMLdata(word, "sug");

		Document doc = convertStringToXMLDocument(xml);

		NodeList nList = doc.getElementsByTagName("suggestion");

		for (int i = 0; i < nList.getLength(); i++) {

			data.add(nList.item(i).getTextContent());

		}

		return data;

	}

	public static ArrayList<HashMap<String, Object>> synonyms_data(String word) throws Exception {

		ArrayList<HashMap<String, Object>> all_syns = new ArrayList<HashMap<String, Object>>();
		ArrayList<String> data = new ArrayList<String>();
		HashMap<String, Object> hash = new HashMap<String, Object>();

		String xml = getXMLdata(word, "syn");

		Document doc = convertStringToXMLDocument(xml);

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

		return all_syns;

	}

	public static ArrayList<HashMap<String, Object>> definitions_data(String word) throws Exception {

		all_definitions.clear();
		String json_code = getXMLdata(word, "dico");
		JSONObject json = new JSONObject(json_code);
		return get_defs_data(json);

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
