package de.gta.sa.radio.station.song;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;

public class Song {

	private File file;
	
	private String filename;
	
	private MP3AudioHeader audioHeader;
	
	private String artist;
	
	private String title;
	
	public Song(String filename) {
		this.filename = filename;
		file = new File(filename);
		
		try {
			MP3File f = (MP3File) AudioFileIO.read(file);
			AbstractID3v2Tag v2tag = f.getID3v2Tag();
			artist = v2tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
			title = v2tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
			audioHeader = (MP3AudioHeader) f.getAudioHeader();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}
	
	public long getTrackLengthMilliseconds() {
		return (long) (audioHeader.getPreciseTrackLength() * 1000);
	}

	public File getFile() {
		return file;
	}

	public String getFilename() {
		return filename;
	}

	/**
	 * Stream has to be determined each time it's needed, because closing a stream (e.g. when skipping radio station)
	 * stream can not be opened again.
	 * @return stream of mp3
	 */
	public FileInputStream getFileInputStream() {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return stream;
	}

	public MP3AudioHeader getAudioHeader() {
		return audioHeader;
	}

	public String getArtist() {
		return artist;
	}

	public String getTitle() {
		return title;
	}
}
