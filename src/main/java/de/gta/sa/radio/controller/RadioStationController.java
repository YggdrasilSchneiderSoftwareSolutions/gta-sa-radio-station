package de.gta.sa.radio.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.gta.sa.radio.station.RadioStation;
import de.gta.sa.radio.station.RadioStationName;
import de.gta.sa.radio.station.factory.RadioStationFactory;
import de.gta.sa.radio.ui.model.RadioModel;

public class RadioStationController {
	
	private Map<RadioStationName, RadioStation> radioStations;
	
	public static final long RADIO_START_TIME = System.currentTimeMillis();
	
	private RadioStationName actualRadioStation;
	
	private int actualStationIndex;
	
	private RadioStream radioStream;
	
	private RadioModel model;

	private class RadioStream extends Thread {
		
		private RadioStation radioStation;
		
		public RadioStream() {
			radioStation = getRadioStations().get(actualRadioStation);
		}
		
		@Override
		public void run() {
			radioStation.play();
		}
		
		public void stopMusicThread() {
			// Wait 500ms in case that run() is still trying to start
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			radioStation.stop();
			interrupt();
		}
	}
	
	// Disable jAudioTagger-loggers
	static {
		Logger[] pin = new Logger[]{ Logger.getLogger("org.jaudiotagger") };

	    for (Logger l : pin)
	        l.setLevel(Level.OFF);
	}
	
	public RadioStationController() {
		// Random start radio station
		actualStationIndex = new Random().ints(0, RadioStationName.values().length).limit(1).findFirst().getAsInt();
	}
	
	public void initializeRadioStations() {
		if (radioStations == null) {
			radioStations = new HashMap<>();
		} else if (!radioStations.isEmpty()) {
			radioStations.clear();
		}
		
		radioStations.put(RadioStationName.BOUNCE_FM, RadioStationFactory
				.createRadioStationByName(RadioStationName.BOUNCE_FM));
		radioStations.put(RadioStationName.COMMERCIALS, RadioStationFactory
				.createRadioStationByName(RadioStationName.COMMERCIALS));
		radioStations.put(RadioStationName.CSR_103_2, RadioStationFactory
				.createRadioStationByName(RadioStationName.CSR_103_2));
		radioStations.put(RadioStationName.K_DST, RadioStationFactory
				.createRadioStationByName(RadioStationName.K_DST));
		radioStations.put(RadioStationName.K_JAH_WEST, RadioStationFactory
				.createRadioStationByName(RadioStationName.K_JAH_WEST));
		radioStations.put(RadioStationName.K_ROSE, RadioStationFactory
				.createRadioStationByName(RadioStationName.K_ROSE));
		radioStations.put(RadioStationName.MASTER_SOUNDS_98_3, RadioStationFactory
				.createRadioStationByName(RadioStationName.MASTER_SOUNDS_98_3));
		radioStations.put(RadioStationName.PLAYBACK_FM, RadioStationFactory
				.createRadioStationByName(RadioStationName.PLAYBACK_FM));
		radioStations.put(RadioStationName.RADIO_LOS_SANTOS, RadioStationFactory
				.createRadioStationByName(RadioStationName.RADIO_LOS_SANTOS));
		radioStations.put(RadioStationName.RADIO_X, RadioStationFactory
				.createRadioStationByName(RadioStationName.RADIO_X));
		radioStations.put(RadioStationName.SF_UR, RadioStationFactory
				.createRadioStationByName(RadioStationName.SF_UR));
		
		radioStations.forEach((name, station) -> station.setController(this));
	}
	
	public void startRadioStream() {
		
		actualRadioStation = RadioStationName.values()[actualStationIndex];
		model.actualizeRadioStationInfo();

		radioStream = new RadioStream();
		radioStream.start();
	}
	
	public void nextRadioStation() {
		radioStream.stopMusicThread();
		
		++actualStationIndex;
		if (actualStationIndex >= RadioStationName.values().length) {
			actualStationIndex = 0;
		}
		
		startRadioStream();
	}
	
	public void previousRadioStation() {
		radioStream.stopMusicThread();
		
		--actualStationIndex;
		if (actualStationIndex < 0) {
			actualStationIndex = RadioStationName.values().length - 1;
		}
		
		startRadioStream();
	}

	public void stopRadioStream() {
		radioStream.stopMusicThread();
	}
	
	/**
	 * Get radio station name as text from enum
	 * @return radio station name
	 */
	public String getActualRadioStationInfo() {
		return RadioStationFactory.STATION_NAME_FOLDER_MAPPING.get(actualRadioStation);
	}
	
	public String getActualSongInfo() {
		// Wait until music plays
		while (!radioStream.radioStation.isPlaying()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
					
		String artist = radioStream.radioStation.getCurrentSong().getArtist();
		String title = radioStream.radioStation.getCurrentSong().getTitle();
		
		return artist + " - " + title;
	}

	public Map<RadioStationName, RadioStation> getRadioStations() {
		return radioStations;
	}

	/**
	 * Get radio station enum value
	 * @return enum value of radio station
	 */
	public RadioStationName getActualRadioStation() {
		return actualRadioStation;
	}
	
	public RadioModel getModel() {
		return model;
	}

	public void setModel(RadioModel model) {
		this.model = model;
	}
	
	public void actualizeSongInfo() {
		model.actualizeSongInfo();
	}
}
