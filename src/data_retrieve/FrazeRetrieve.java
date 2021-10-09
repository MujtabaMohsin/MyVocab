package data_retrieve;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
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



	public static String getAPIdata(String word, String type) throws Exception {
		 
		if (word.contains(" ")) {
			word = word.replaceAll("\\s","+");
			 
		}
		
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
		
		URL url = new URL(uri);
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line + "\n");
		}
		
//		HttpClient client = HttpClient.newHttpClient();
//		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
//
//		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		//System.out.println(response.body());
		// XML data
		return stringBuilder.toString();
	}

	public static Document convertStringToXMLDocument(String xmlString) {
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

	

	

}
