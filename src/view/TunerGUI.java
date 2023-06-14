package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.TunerController;
import model.Tuning;
import model.Instruments;
import model.Tuner;

public class TunerGUI extends JFrame implements PropertyChangeListener {
	
	private JPanel northPanel;
	private JPanel southPanel;
	private JButton start;
	private JButton stop;
	private JLabel instrumentLabel;
	protected JLabel tuningLabel;
	private JComboBox<Instruments> instrumentChooser;
	protected JComboBox<String> tuningChooser;
	protected JLabel currentNote;
	
	protected TunerController controller;

	public TunerGUI(String title) {
		this.setTitle(title);
		this.setSize(250, 350);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.initCamps();
		this.setVisible(true);
	}
	
	private void initCamps() {
		this.northPanel = new JPanel();
		this.northPanel.setLayout(new BoxLayout(this.northPanel, BoxLayout.PAGE_AXIS));
		this.southPanel = new JPanel(new BorderLayout());
		this.start = new JButton("Start");
		this.stop = new JButton("Stop");
		this.instrumentLabel = new JLabel("Instrument:");
		this.instrumentChooser = new JComboBox<>(Instruments.values());
		this.instrumentChooser.setSelectedItem(null);
		
		this.currentNote = new JLabel("      try to play something");
		
		this.northPanel.add(this.instrumentLabel);
		this.northPanel.add(this.instrumentChooser);

		this.southPanel.add(this.start, BorderLayout.NORTH);
		this.southPanel.add(this.stop, BorderLayout.CENTER);
		
		this.add(this.northPanel, BorderLayout.NORTH);
		this.add(this.currentNote, BorderLayout.CENTER);
		this.add(this.southPanel, BorderLayout.SOUTH);
	}
	
	public void addController(TunerController controller) {
		this.controller = controller;
		
		this.start.addActionListener(this.controller);
		this.stop.addActionListener(this.controller);
		
		this.instrumentChooser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				clearPreviousSelection();
				
				JComboBox<?> combo = (JComboBox<?>) evt.getSource();
				Instruments instrument = (Instruments) combo.getSelectedItem();
				Instruments selectedInstrument;

				if (instrument == null) {
					System.out.println("no instruments were selected!");
					return;
				}

				if (instrument.name.equals(Instruments.FourStringBass.name)) {
					selectedInstrument = Instruments.FourStringBass;
				} else if (instrument.name.equals(Instruments.ElectricGuitar.name) ) {
					selectedInstrument = Instruments.ElectricGuitar;
				} else {
					selectedInstrument = null;
				}

				if (selectedInstrument == null) {
					return;
				}
				String[] tunings = new String[selectedInstrument.tunings.size()];
				System.out.println("number of registered tunings: " + selectedInstrument.tunings.size());

				int i = 0;

				for (Tuning tuning : selectedInstrument.tunings) {
					tunings[i] = tuning.name;
					i++;
				}
				
				tuningLabel = new JLabel("Tuning:");
				tuningChooser = new JComboBox<String>(tunings);
				northPanel.add(tuningLabel);
				northPanel.add(tuningChooser);
				northPanel.revalidate();
			}
		});
	}
	
	protected void clearPreviousSelection() {
		Component[] components = this.northPanel.getComponents();
		int index = components.length;
		if (index >= 4) {
			this.northPanel.remove(components[index - 1]);
			this.northPanel.remove(components[index - 2]);
			System.out.println("cleared");			
		}
	}
	
	public Tuner startTuning(Component[] components) {
		Component[] comps2 = ((JPanel) components[0]).getComponents();
		JComboBox<?> instrumentChooser;
		JComboBox<?> tuningChooser; 
		try {
			instrumentChooser = (JComboBox<?>) comps2[1];
			tuningChooser = (JComboBox<?>) comps2[3];
			Instruments instrument = (Instruments) instrumentChooser.getSelectedItem();
			String tuning = (String) tuningChooser.getSelectedItem();
			try {
				if (instrument == null) {
					return null;
				}
				return this.createTuner(instrument, tuning);
			} catch (NullPointerException e) {
				System.out.println("null pointer");
			} 
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("index out of bound");
		}
		
		return null;
	}
	
	private Tuner createTuner(Instruments instrument, String tuning) {

		switch (instrument) {
			case FourStringBass:
				System.out.println(instrument.name);
				System.out.println(tuning);
				return new Tuner(Instruments.FourStringBass, tuning, 3.5F);
			case ElectricGuitar:
				System.out.println(instrument.name);
				System.out.println(tuning);
				return new Tuner(Instruments.ElectricGuitar, tuning, 3.5F);
		default:
				return null;
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("newPitch")) {
			this.currentNote.setText("      " + evt.getNewValue());
			this.revalidate();
		}
	}
	
	public static void main(String[] args) {
		TunerGUI tunerGui = new TunerGUI("tuner");
		
		tunerGui.addController(new TunerController(tunerGui));
	}
}
