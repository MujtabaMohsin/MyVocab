package data_retrieve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class OxfordRetrieve {

	public static JSONObject getJSONcode(String word, String type) {

		String restUrl = "";
		String language = "en";

		final String strictMatch = "false";
		final String word_id = word.toLowerCase();
		final String source_lang_translate = "en";
		final String target_lang_translate = "ar";

		if (type.equals("translations")) {
			final String fields = "translations";
			restUrl = "https://od-api.oxforddictionaries.com:443/api/v2/translations/" + source_lang_translate + "/"
					+ target_lang_translate + "/" + word_id;
		} 
		else if (type.equals("entries")) {

			language = "en-gb";

			final String fields = "definitions,examples";

			restUrl = "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?"
					+ "fields=" + fields + "&strictMatch=" + strictMatch;

		}
		
		
		else if (type.equals("sentences")) {

			language = "en";

			final String fields = "definitions,examples";

			restUrl = "https://od-api.oxforddictionaries.com:443/api/v2/sentences/" + language + "/" + word_id + "?"
					+ "&strictMatch=" + strictMatch;

		}
 
		// TODO: replace with your own app id and app key
		final String app_id = "e70bfafc";
		final String app_key = "2eeeab9fe1b5a6e679e5553865fc985e";

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

		JSONObject jsonObj = null;
		//System.out.println(stringBuilder.toString());
		if (stringBuilder.toString().startsWith("{")) {
			jsonObj = new JSONObject(stringBuilder.toString());
		}

		return jsonObj;

	}

}
