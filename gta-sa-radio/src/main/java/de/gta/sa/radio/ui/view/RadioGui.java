package de.gta.sa.radio.ui.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.gta.sa.radio.ui.model.RadioModel;

public class RadioGui extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private RadioModel model;
	
	private JLabel radioStationLabel;
	
	private JLabel songLabel;
	
	private JButton playButton;
	
	private JButton stopButton;
	
	private JButton nextButton;
	
	private JButton previousButton;
	
	public RadioGui(RadioModel model) {
		this.model = model;
		
		setTitle("GTA San Andreas Radio Station");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel mp3InfoPanel = new JPanel();
		mp3InfoPanel.setLayout(new GridLayout(0, 1));
		radioStationLabel = new JLabel("Radio Station");
		radioStationLabel.setHorizontalAlignment(JLabel.CENTER);
		songLabel = new JLabel("Artist - Song");
		mp3InfoPanel.add(radioStationLabel);
		mp3InfoPanel.add(songLabel);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		playButton = new JButton("Play");
		playButton.addActionListener(event -> {
			model.playClicked();
		});
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(event -> {
			model.stopClicked();
		});
		stopButton.setEnabled(false);
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(event -> {
			model.nextClicked();
		});
		nextButton.setEnabled(false);
		
		previousButton = new JButton("Prev");
		previousButton.addActionListener(event -> {
			model.previousClicked();
		});
		previousButton.setEnabled(false);
		
		buttonPanel.add(playButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(nextButton);
		buttonPanel.add(previousButton);
		
		mainPanel.add(mp3InfoPanel);
		mainPanel.add(buttonPanel);
		
		add(mainPanel);
		
		pack();
		setVisible(true);
	}

	public JLabel getRadioStationLabel() {
		return radioStationLabel;
	}

	public JLabel getSongLabel() {
		return songLabel;
	}

	public JButton getPlayButton() {
		return playButton;
	}

	public JButton getStopButton() {
		return stopButton;
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public JButton getPreviousButton() {
		return previousButton;
	}

	public RadioModel getModel() {
		return model;
	}
	
}
