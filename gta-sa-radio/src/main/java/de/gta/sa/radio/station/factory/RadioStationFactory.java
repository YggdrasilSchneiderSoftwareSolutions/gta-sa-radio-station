package de.gta.sa.radio.station.factory;

import java.util.HashMap;
import java.util.Map;

import de.gta.sa.radio.station.RadioStation;
import de.gta.sa.radio.station.RadioStationName;
import de.gta.sa.radio.station.impl.RadioStationImpl;

public class RadioStationFactory {
	
	public static final Map<RadioStationName, String> STATION_NAME_FOLDER_MAPPING = new HashMap<>();
	
	static {
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.BOUNCE_FM, "Bounce FM");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.COMMERCIALS, "Commercials");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.CSR_103_2, "CSR 103.2");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.K_DST, "K-DST");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.K_JAH_WEST, "K-Jah West");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.K_ROSE, "K-Rose");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.MASTER_SOUNDS_98_3, "Master Sounds 98.3");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.PLAYBACK_FM, "Play Back FM");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.RADIO_LOS_SANTOS, "Radio Los Santos");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.RADIO_X, "Radio X");
		STATION_NAME_FOLDER_MAPPING.put(RadioStationName.SF_UR, "SF-UR");
	}

	public static RadioStation createRadioStationByName(RadioStationName stationName) {
		return new RadioStationImpl(STATION_NAME_FOLDER_MAPPING.get(stationName));
	}
}
