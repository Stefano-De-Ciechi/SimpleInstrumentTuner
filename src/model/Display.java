package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import processing.core.PApplet;

/*
* Using the Processing library to make a simple (and kinda ugly) visualization
*/
public class Display extends PApplet implements PropertyChangeListener {

	private Display thisSketch;		// maintaining a reference to this instance
	int offset;
	float w, h;
	
	public void settings() {
		size(400, 400);

		this.w = (float) width / 2;
		this.h = (float) height / 2;
	}
	
	public void setup() {
		frameRate(60);
	}
	
	public void draw() {
		background(0);
		strokeWeight(3);
		stroke(255);
		noFill();
		//line(0, height / 2, width, height / 2);
		ellipse(this.w, this.h, 100, 100);
		//stroke(120, 200, 80);
		stroke(0, 50, 250);
		//line(0, height / 2 - this.offset, width, height / 2 - this.offset);
		ellipse(this.w, this.h, 100 - this.offset, 100 - this.offset);
	}
	
	public void startDisplay(Display sketch) {
		if (this.thisSketch == null) {
			this.thisSketch = sketch;
			String[] args = {"model.Display"};
			PApplet.runSketch(args, this.thisSketch);			
		} else {
			this.thisSketch.getSurface().setVisible(true);
			this.thisSketch.start();
		}
	}
	
	public void stopDisplay() {
		this.thisSketch.stop();
		this.thisSketch.getSurface().setVisible(false);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("newPitch")) {
			this.parsePitch((String) evt.getNewValue());
		}
	}
	
	private void parsePitch(String pitchStr) {
		this.offset = parseInt(pitchStr.split(" ")[1]) * 10;
	}
}
