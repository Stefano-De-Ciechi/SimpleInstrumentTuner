package model;

import javax.sound.sampled.LineUnavailableException;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;

/*
* I'll try to explain why I used Threads (if I understood well):
* I'm using Threads to be able to stop the execution of the pitch detection algorithm from the audio dispatcher
* without interrupting the execution of the entire program.
* To do that, the class PitchDetect must implement the Runnable interface, which requires the override of the run()
* method, which is executed when calling the start() method on a Thread instance initialized with the PitchDetect class.
* This Thread instance, in this case, is the this.execution variable of the PitchDetect class.
*/

public class PitchDetect implements Runnable {

	public AudioDispatcher audioDispatcher;
	public float sampleRate;
	public int audioBufferSize = 1024;
	public float threshold;
	public PitchDetectionHandler pHandler;
	
	private Thread execution;	// multithreading stuff to be able to stop the execution
	
	public PitchDetect(PitchDetectionHandler pHandler) {
		this.sampleRate = 44100;
		this.threshold = 3.0F;
		this.pHandler = pHandler;
		
		try {
			this.audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(this.audioBufferSize * 2, 0);	// input from the default microphone
		} catch (LineUnavailableException e) {
			System.err.println("Unable to access microphone");
		}
		
		PitchEstimationAlgorithm algorithm = PitchEstimationAlgorithm.DYNAMIC_WAVELET;
		AudioProcessor pitchEstimator = new PitchProcessor(algorithm, this.sampleRate, this.audioBufferSize * 2, pHandler);
		
		this.audioDispatcher.addAudioProcessor(pitchEstimator);
	}
	
	public PitchDetect(PitchDetectionHandler pHandler, float threshold) {
		this(pHandler);
		this.threshold = threshold;
	}
	
	public void detectPitch() {
		this.execution = new Thread(this);
		this.execution.start();
	}
	
	public void stop() {
		this.execution.interrupt();
		this.audioDispatcher.stop();
	}

	@Override
	public void run() {
		this.audioDispatcher.run();
	}
}