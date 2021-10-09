package data_retrieve;

public class WordModel {
	
	String id;
	int number;
	String word;
	String result;

	public WordModel() {
		this.id = "";
		this.number = 0;
		this.word = "";
		this.result = "";

	}

	public WordModel(String id ,int number, String word, String result) {
		this.id = id;
		this.number = number;
		this.word = word;
		this.result = result;

	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
