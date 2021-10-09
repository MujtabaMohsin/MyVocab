module MyVocab {
	exports application;
	exports data_retrieve;
	exports views;
	exports word_tabs;

	requires java.net.http;
	requires java.sql;
	requires java.xml;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.web;
	 
	requires org.json;
	requires javafx.media;
	requires java.desktop;
	
	
	opens application;
	opens views;
	opens data_retrieve;
	opens quiz;
 
	opens word_tabs;
 
	 
}