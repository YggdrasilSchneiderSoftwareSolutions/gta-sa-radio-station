package de.gta.sa.radio.station;

import de.gta.sa.radio.controller.RadioStationController;
import de.gta.sa.radio.station.song.Song;

public interface RadioStation {
	void play();
	void stop();
	Song getCurrentSong();
	boolean isPlaying();
	RadioStationController getController();
	void setController(RadioStationController controller);
}
