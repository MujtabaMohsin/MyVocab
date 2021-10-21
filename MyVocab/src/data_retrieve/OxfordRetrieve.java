package data_retrieve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class OxfordRetrieve {
	
	ArrayList<String> all_translations = new ArrayList<String>();

	public static JSONObject getJSONcode(String word) {
		final String language = "en";

		final String fields = "translations";
		final String strictMatch = "false";
		final String word_id = word.toLowerCase();
		final String source_lang_translate = "en";
		final String target_lang_translate = "ar";

		final String restUrl = "https://od-api.oxforddictionaries.com:443/api/v2/translations/" + source_lang_translate
				+ "/" + target_lang_translate + "/" + word_id;

		// TODO: replace with your own app id and app key
		final String app_id = ""; // CHANGE IT
		final String app_key = ""; // CHANGE IT

		StringBuilder stringBuilder = new StringBuilder();

		try {
			URL url = new URL(restUrl);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestProperty("app_id", app_id);
			urlConnection.setRequestProperty("app_key", app_key);

			// read the output from the server
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject jsonObj = new JSONObject(stringBuilder.toString());

		return jsonObj;

	}

	public static ArrayList<String> getTranslations(String word) {
		
		
		JSONArray keys = json.names();
		 
		for (int i = 0; i < keys.length(); i++) {
			
			String key = keys.getString(i); // Here's your key
			Object keyvalue = json.get(key); // Here's your value
			
			 

			if (keyvalue instanceof JSONArray) {

				if (key.equals("definitions")) {

					//definitions.add(keyvalue.toString());

				}

				else if (!(key.equals("dialects"))) {

					System.out.println(key + "---" + "array");


					for (int j = 0; j < ((JSONArray) keyvalue).length(); j++) {
						retrive(((JSONArray) keyvalue).getJSONObject(j));
					}
				}

			}

			else if (keyvalue instanceof JSONObject) {

				 

				System.out.println(key + "---" + "object");
				retrive((JSONObject) keyvalue);
			}

			else {

				// -------values------


				System.out.println(key + "---" + "value");

			}

		}
		
		return all_translations;
		

	}

}
