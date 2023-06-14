package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Display;
import model.Tuner;
import view.TunerGUI;

public class TunerController implements ActionListener {

	protected TunerGUI view;
	protected Tuner tuner;
	protected Display display;
	
	public TunerController(TunerGUI view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Start")) {
			Component[] components = ((JButton) evt.getSource()).getParent().getParent().getComponents();
			this.tuner = this.view.startTuning(components);
			if (this.tuner != null) {
				this.tuner.addListener(this.view);
				this.tuner.start();
			}
			this.display = new Display();
			this.tuner.addListener(this.display);
			this.display.startDisplay(this.display);
		}
		
		if (evt.getActionCommand().equals("Stop")) {
			if (this.tuner != null) {
				this.tuner.stop();
				this.tuner = null;
			}
			this.display.stopDisplay();
		}
		
	}

}
