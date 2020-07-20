package de.gta.sa.radio.ui.model;

import de.gta.sa.radio.controller.RadioStationController;
import de.gta.sa.radio.ui.view.RadioGui;

public interface RadioModel {

	RadioStationController getController();
	void playClicked();
	void stopClicked();
	void nextClicked();
	void previousClicked();
	void setRadioGui(RadioGui gui);
	void actualizeSongInfo();
	void actualizeRadioStationInfo();
}
