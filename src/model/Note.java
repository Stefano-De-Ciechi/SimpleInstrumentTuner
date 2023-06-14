package model;

public class Note {
	public String modernNotation;	// A, B, C, ...
	public String classicNotation;	// Do, Re, Mi, ...
	public float freq;
	
	public Note(String modernNotation, float freq) {
		this.modernNotation = modernNotation;
		this.classicNotation = "";
		this.freq = freq;
	}
	
	public Note(String modernNotation, String classicNotation, float freq) {
		this(modernNotation, freq);
		this.classicNotation = classicNotation;
	}
	
}
