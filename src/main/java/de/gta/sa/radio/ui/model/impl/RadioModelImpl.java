package de.gta.sa.radio.ui.model.impl;

import de.gta.sa.radio.controller.RadioStationController;
import de.gta.sa.radio.ui.model.RadioModel;
import de.gta.sa.radio.ui.view.RadioGui;

public class RadioModelImpl implements RadioModel {
	
	private RadioGui gui;
	
	private RadioStationController controller;
	
	public RadioModelImpl(RadioStationController controller) {
		this.controller = controller;
		controller.initializeRadioStations();;
	}
	
	@Override
	public void setRadioGui(RadioGui gui) {
		this.gui = gui;
	}
	
	@Override
	public RadioStationController getController() {
		return controller;
	}

	@Override
	public void playClicked() {
		controller.startRadioStream();
		
		gui.getPlayButton().setEnabled(false);
		gui.getStopButton().setEnabled(true);
		gui.getNextButton().setEnabled(true);
		gui.getPreviousButton().setEnabled(true);
	}

	@Override
	public void stopClicked() {
		controller.stopRadioStream();

		gui.getPlayButton().setEnabled(true);
		gui.getStopButton().setEnabled(false);
		gui.getNextButton().setEnabled(false);
		gui.getPreviousButton().setEnabled(false);

		gui.getSongLabel().setText("music stopped");
	}

	@Override
	public void nextClicked() {
		controller.nextRadioStation();
	}

	@Override
	public void previousClicked() {
		controller.previousRadioStation();
	}

	@Override
	public void actualizeSongInfo() {
		gui.getSongLabel().setText(controller.getActualSongInfo());
	}

	@Override
	public void actualizeRadioStationInfo() {
		gui.getRadioStationLabel().setText(controller.getActualRadioStationInfo());
	}

}
