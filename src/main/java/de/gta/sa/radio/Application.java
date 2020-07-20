package de.gta.sa.radio;

import de.gta.sa.radio.controller.RadioStationController;
import de.gta.sa.radio.ui.model.RadioModel;
import de.gta.sa.radio.ui.model.impl.RadioModelImpl;
import de.gta.sa.radio.ui.view.RadioGui;

public class Application {

	public static void main(String[] args) {
		System.out.println("Starting GTA San Andreas Radio Station....");
		
		RadioStationController ctrl = new RadioStationController();
		RadioModel model = new RadioModelImpl(ctrl);
		RadioGui gui = new RadioGui(model);
		model.setRadioGui(gui);
		ctrl.setModel(model);
		
		System.out.println("GTA San Andreas Radio Station started");
	}

}
