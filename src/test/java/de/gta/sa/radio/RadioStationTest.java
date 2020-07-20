package de.gta.sa.radio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.gta.sa.radio.controller.RadioStationController;
import de.gta.sa.radio.station.RadioStationName;
import de.gta.sa.radio.station.factory.RadioStationFactory;
import de.gta.sa.radio.ui.model.RadioModel;
import de.gta.sa.radio.ui.view.RadioGui;

public class RadioStationTest {
	
	static RadioStationController ctrl;
	
	private static class RadioModelMock implements RadioModel {

		@Override
		public RadioStationController getController() {
			return null;
		}

		@Override
		public void playClicked() {
			
		}

		@Override
		public void stopClicked() {
			
		}

		@Override
		public void nextClicked() {
			
		}

		@Override
		public void previousClicked() {
			
		}

		@Override
		public void setRadioGui(RadioGui gui) {
			
		}

		@Override
		public void actualizeSongInfo() {
			
		}

		@Override
		public void actualizeRadioStationInfo() {
			
		}
		
	}
	
	@BeforeClass
	public static void setup() {
		ctrl = new RadioStationController();
		ctrl.setModel(new RadioModelMock());
		ctrl.initializeRadioStations();
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		System.out.println("@BeforeClass Actual Threads: " + threadSet.size());
		threadSet.forEach(System.out::println);
	}
	
	@Test
	public void testControllerInit() {
		assertTrue(ctrl.getRadioStations().size() == RadioStationName.values().length);
	}

	@Test
	public void testSkipping() throws InterruptedException {
		ctrl.startRadioStream();
		RadioStationName initialRadioStation = ctrl.getActualRadioStation();
		assertEquals(ctrl.getActualRadioStationInfo(), 
				RadioStationFactory.STATION_NAME_FOLDER_MAPPING.get(initialRadioStation));
		Thread.sleep(2000);
		
		ctrl.nextRadioStation();
		assertNotEquals(initialRadioStation, ctrl.getActualRadioStation());
		Thread.sleep(5000);
		
		ctrl.previousRadioStation();
		assertEquals(initialRadioStation, ctrl.getActualRadioStation());
		ctrl.previousRadioStation();
		Thread.sleep(2000);
	}
	
	@AfterClass
	public static void tearDown() {
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		System.out.println("@AfterClass Actual Threads: " + threadSet.size());
		threadSet.forEach(System.out::println);
		
		ctrl = null;
	}
}
