package de.gta.sa.radio.station.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import de.gta.sa.radio.controller.RadioStationController;
import de.gta.sa.radio.station.RadioStation;
import de.gta.sa.radio.station.song.Song;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class RadioStationImpl implements RadioStation {
	
	/**
	 * Milliseconds per frame of MP3-file.
	 * = Samples per frame / frame rate * 1000 
	 * -> (1152 / 48000) * 1000
	 * all info found in the Mp3AudioHeader 
	 */
	public static double MS_PER_FRAME = 24;
	
	protected List<Song> songsPlayList;
	
	private static final String MP3_DIRECTORY = "mp3/";
	
	private String stationName;
	
	private AdvancedPlayer actualSong;
	
	private Song currentSong;
	
	public volatile boolean playing;
	
	private RadioStationController controller;
	
	public RadioStationImpl(String stationName) {
		this.stationName = stationName;
		songsPlayList = new ArrayList<>();
		
		initializeSongs();
	}
	
	/**
	 * Walk through MP3-directory and radio station folder and filter for mp3-files to add them to 
	 * song list
	 */
	private void initializeSongs() {
		try (Stream<Path> dirPath = Files.walk(Paths.get(MP3_DIRECTORY + stationName + "/"))) {
			dirPath
				.filter(file -> Files.isRegularFile(file)
						&& file.getFileName().toString().endsWith(".mp3"))
				.forEach(mp3 -> songsPlayList.add(new Song(mp3.toString())));
			
			// Shuffle for random order
			Collections.shuffle(songsPlayList);
			
			System.out.println("Radio Station " + stationName + "'s Songlist: ");
			songsPlayList.forEach(song -> System.out.println(song.getFilename()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void play() {
		boolean switchedStation = true;
		long[] currentSongAndPosition = getSongAndPosition();

		int songIndex = (int) currentSongAndPosition[0];
		playing = true;
		while (playing) {
			currentSong = songsPlayList.get(songIndex++);
			long totalFrames = currentSong.getAudioHeader().getNumberOfFrames();
			try {
				actualSong = new AdvancedPlayer(currentSong.getFileInputStream());
				System.out.println("Playing " + currentSong.getFile().getName() + " on " + stationName);
	
				int startAtFramePos = 0;
				if (switchedStation) { // only calculate frame position if station has switched
					startAtFramePos = (int) (currentSongAndPosition[1] / MS_PER_FRAME);
					switchedStation = false;
				}
				controller.actualizeSongInfo();
				actualSong.play(startAtFramePos, (int) totalFrames);
				
				// Repeat if end of stream
				if (songIndex == songsPlayList.size()) {
					songIndex = 0;
				}
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void stop() {
		if (actualSong != null) {
			playing = false;
			actualSong.close();
			actualSong = null;
		}
	}

	@Override
	public Song getCurrentSong() {
		return currentSong;
	}
	
	private long[] getSongAndPosition() {
		long[] songIdxAndMs = new long[2];
		long songsDurationSum = 0L;
		long timePassedSinceStart = System.currentTimeMillis() - RadioStationController.RADIO_START_TIME;
		for (int i = 0; i < songsPlayList.size(); i++) {
			Song song = songsPlayList.get(i);
			songsDurationSum += song.getTrackLengthMilliseconds();
			
			if (songsDurationSum >= timePassedSinceStart) {
				songIdxAndMs[0] = i; // Song index
				songIdxAndMs[1] = song.getTrackLengthMilliseconds() - (songsDurationSum - timePassedSinceStart); // Song position
				break;
			}
			
			// Repeat if necessary
			if (i + 1 == songsPlayList.size()) {
				i = 0;
			}
		}
		
		return songIdxAndMs;
	}

	@Override
	public boolean isPlaying() {
		return playing;
	}

	@Override
	public RadioStationController getController() {
		return controller;
	}

	@Override
	public void setController(RadioStationController controller) {
		this.controller = controller;
	}

}
