package quiz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import data_retrieve.DBretrieve;

public class TakeQuiz {

	public static ArrayList<String> getAllWords(String type) throws SQLException {

		ArrayList<String> all_words = new ArrayList<String>();

		ArrayList<String> all_words_random = new ArrayList<String>();
		String def_list_id = data_retrieve.DBretrieve.getDefualtList();
		all_words = DBretrieve.getQuizWords(type, def_list_id);
		Random rand = new Random();
		int num_of_words = all_words.size();

		if (!type.equals("fewest")) {
			for (int i = 0; i < num_of_words; i++) {

				int curr_random = rand.nextInt(all_words.size());
				// System.out.println(all_words);
				all_words_random.add(all_words.get(curr_random));
				all_words.remove(curr_random);

			}
		} else {
			all_words_random = all_words;
		}

		return all_words_random;

	}

	public static String getQuestion(ArrayList<String> all_words) throws Exception {
		ArrayList<String> words = all_words;

		ArrayList<String> answer_examples = new ArrayList<String>();

		String answer = words.get(0);
		answer_examples = word_tabs.Exmples.examples_data(answer);
		Random rand = new Random();
		int random = rand.nextInt(answer_examples.size());
		String question = answer_examples.get(random);

		// System.out.println(question);

		return question;
	}

	public static ArrayList<String> getChoices(ArrayList<String> all_words, String answer) throws SQLException {
		ArrayList<String> words = new ArrayList<String>();
		words = (ArrayList<String>) all_words.clone();

		ArrayList<String> all_choices = new ArrayList<String>();
		all_choices.add(answer);

		for (int i = 0; i < 3; i++) {

			Random rand = new Random();
			int random = rand.nextInt(words.size());
			if (!words.get(random).equals(answer)) {
				all_choices.add(words.get(random));
				words.remove(random);
			} else {
				words.remove(random);
				int random2 = rand.nextInt(words.size() - 1);
				all_choices.add(words.get(random2));
			}

		}

		return all_choices;

	}

	public static ArrayList<String> getRandomChoices(ArrayList<String> all_choices) {

		ArrayList<String> choices = (ArrayList<String>) all_choices.clone();
		ArrayList<String> new_choices = new ArrayList<String>();

		for (int i = 0; i < 4; i++) {
			Random rand = new Random();
			int random = rand.nextInt(choices.size());
			new_choices.add(choices.get(random));
			choices.remove(random);
		}

		return new_choices;

	}

}
