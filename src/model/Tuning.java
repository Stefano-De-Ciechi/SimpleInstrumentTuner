package model;

public class Tuning {
	public String name;
	public Note[] note;
	
	public Tuning(String nome, Note[] note) {
		this.name = nome;
		this.note = note;
	}
	
	public void printTuning() {		// debug method
		System.out.println("Tuning: " + this.name);
		for (Note n : this.note) {
			System.out.println(n.modernNotation + " ( " + n.classicNotation + " ) : " + n.freq + " Hz");
		}
	}
}
