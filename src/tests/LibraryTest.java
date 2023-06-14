package tests;

//import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
//import be.tarsos.dsp.filters.HighPass;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
//import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;

/*
* a sample program to test if the TarsoDSP library is working
*/

public class LibraryTest {

	public AudioDispatcher d;
	public int sampleRate;
	
	public float threshold;
	//AudioProcessor highPass;
	
	public LibraryTest() {
		this.sampleRate = 44100;
		//this.highPass = new HighPass(40, this.sampleRate);
		this.threshold = 3.0f;
	}
	
	public void microphoneTest() {
		try {
			d = AudioDispatcherFactory.fromDefaultMicrophone(this.sampleRate, 1024 * 2, 0);	// input audio from default microphone
		} catch (LineUnavailableException e) {
			System.err.println("Can't get access to the microphone");
		}
		
		//d.addAudioProcessor(this.highPass);
		
		PitchDetectionHandler printPitch = new PitchDetectionHandler() {
			
			@Override
			public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {

				float pitch = pitchDetectionResult.getPitch();
				
				if (pitch > 41.203 - threshold && pitch < 41.203 + threshold) {
					System.out.println("E " + (pitch - 41.203));
				}
				
			}
		};
		
		//PitchEstimationAlgorithm algo = PitchEstimationAlgorithm.YIN;
		PitchEstimationAlgorithm algo = PitchEstimationAlgorithm.DYNAMIC_WAVELET;
		AudioProcessor pitchEstimator = new PitchProcessor(algo, sampleRate, 1024, printPitch);
		
		d.addAudioProcessor(pitchEstimator);
		

		// Add this lines to have an audio player (monitoring ?) --> USE ONLY WITH CONNECTED HEADPHONES AND LOW VOLUME
		/*try {
			d.addAudioProcessor(new AudioPlayer(new AudioFormat(sampleRate, 16, 1, true, true)));
		} catch (LineUnavailableException e) {
			System.err.println("Error while setting up an AudioPlayer");
		}*/
		d.run();
		
	}
	
	public static void main(String[] args) {
		LibraryTest test = new LibraryTest();
		test.microphoneTest();
	}

}
