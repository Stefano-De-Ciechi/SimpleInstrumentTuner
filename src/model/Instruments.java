package model;

import java.util.Arrays;
import java.util.LinkedList;

public enum Instruments {
	
	FourStringBass("4 string bass",
		new Tuning("standard",
			new Note[] {
				new Note("E", 41.203F),
				new Note("A", 55.0F),
				new Note("D", 73.416F),
				new Note("G", 97.999F)
			}
		)
	),
	
	ElectricGuitar("electric guitar",
		new Tuning("standard",
			new Note[] {
				new Note("E", 82.41F),
				new Note("A", 110.0F),
				new Note("D", 146.836F),
				new Note("G", 196.00F),
				new Note("B", 246.94F),
				new Note("E", 329.63F)
			}
		), 
		new Tuning("test", new Note[] { new Note("TEST", 69.0F)})
	);

	// you can add new instruments to this enum
	
	public final String name;
	public final LinkedList<Tuning> tunings;
	
	Instruments(String name, Tuning... tunings) {	// variadic method notation
		this.name = name;
		this.tunings = new LinkedList<>();
		this.tunings.addAll(Arrays.asList(tunings));
	}

	public void listTunings() {		// debug method
		System.out.println("available tunings for '" + this.name + "': ");
		for (Tuning tune : this.tunings) {
			System.out.println("\t- " + tune.name);
		}
	}
	
	public Tuning getTuning(String type) {
		for (Tuning acc : this.tunings) {
			if (acc.name.compareTo(type) == 0) return acc;
		}

		return null;
	}
}
