package tests;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

public class MicrophonesTest {

	public static void main(String[] args) {
		AudioDispatcher ad;
		try {
			ad = AudioDispatcherFactory.fromDefaultMicrophone(2048, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (javax.sound.sampled.Mixer.Info info : AudioSystem.getMixerInfo()) {
			System.out.println(info.getName());
		}

		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        System.out.println("Mixers: " + mixers.length);
	}

}
