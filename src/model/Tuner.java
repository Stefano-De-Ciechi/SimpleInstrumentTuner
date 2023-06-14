package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;

/*
 * Main class of the Model
 */

public class Tuner {

	public Instruments instrument;
	public Tuning tuning;
	public float threshold;
	
	private PitchDetect detect;
	
	protected PropertyChangeSupport propertyChange;
	
	public Tuner(Instruments instrument, String tuning) {
		this.instrument = instrument;
		this.tuning = this.instrument.getTuning(tuning);
		this.threshold = 3.5F;
		this.propertyChange = new PropertyChangeSupport(this);
	}
	
	public Tuner(Instruments instrument, String tuning, float threshold) {
		this(instrument, tuning);
		this.threshold = threshold;
	}
	
	public void start() {
		this.detect = new PitchDetect(new PitchDetectionHandler() {
			
			@Override
			public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
				
				float pitch = pitchDetectionResult.getPitch();
				Note[] note = tuning.note;

				for (Note n : note) {
					float freq = n.freq;
					if (pitch > freq - threshold && pitch < freq + threshold) {
						float res = (pitch - freq);
						String stringRes;
						if (res > 0) stringRes = n.modernNotation + " +" + res;
						else stringRes = n.modernNotation + " " + res;
						System.out.println(n.modernNotation + " " + res);
						propertyChange.firePropertyChange("newPitch", 0, stringRes);    // oldValue and newValue must be different for the property change to function properly
					}
				}
			}
		});
		
		this.detect.detectPitch();
		System.out.println("tuner started");
	}
	
	public void stop() {
		this.detect.stop();
		System.out.println("tuner stopped.");
	}
	
	public void addListener(PropertyChangeListener listener) {
		this.propertyChange.addPropertyChangeListener(listener);
	}
}
