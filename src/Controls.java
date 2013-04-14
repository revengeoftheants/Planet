import processing.core.*;	
import controlP5.*;
import java.awt.Frame;
import java.awt.BorderLayout;

public class Controls {

	PApplet parPApp = null;
	ControlP5 mainCP5;
	ControlFrame cntrlFrame;
	String cntrlFrameNm = "New Frame";
	int wdthNbr = 325;
	int hghtNbr = 150;

	/*
	 * Constructor
	 */
	Controls(PApplet inpParPApp) {
		parPApp = inpParPApp;
		mainCP5 = new ControlP5(parPApp);
		initParms();

		cntrlFrame = addCntrlFrame();
		//crteCntrls();
	}


	/*
	 * Initializes the parameters.
	 */
	void initParms() {
	}


	/*
	 * Creates a new frame for the controls.
	 */
	ControlFrame addCntrlFrame() {
		Frame javaFrame = new Frame(cntrlFrameNm);
		// Create a new PApplet for the ControlFrame.
		cntrlFrame = new ControlFrame(parPApp, wdthNbr, hghtNbr);

		javaFrame.add(cntrlFrame);

		// Initialize the ConrolFrame PApplet.
		cntrlFrame.init();
		javaFrame.setTitle(cntrlFrameNm);
		javaFrame.setSize(cntrlFrame.wdthNbr, cntrlFrame.hghtNbr);
		javaFrame.setLocation(1000, 100);
		javaFrame.setResizable(false);
		javaFrame.setVisible(true);

		return cntrlFrame;
	}


	/*
	 * Creates the controls.
	 */
	/*
	void crteCntrls() {  
		int y = 0;
		cntrlFrame.cp5.addSlider("boxSize", 0, 500, Controller.boxSizeNbr, 10, y += 12, 250, 9).plugTo(parPApp, "boxSize");
		cntrlFrame.cp5.addSlider("translateX", 0, 500, Controller.transXNbr, 10, y += 12, 250, 9).plugTo(parPApp, "translateX");
		cntrlFrame.cp5.addSlider("translateY", 0, 500, Controller.transYNbr, 10, y += 12, 250, 9).plugTo(parPApp, "translateY");
		cntrlFrame.cp5.addSlider("translateZ", 0, 500, Controller.transZNbr, 10, y += 12, 250, 9).plugTo(parPApp, "translateZ");
		cntrlFrame.cp5.addSlider("rotateX", 0, PApplet.TWO_PI, Controller.rotateXNbr, 10, y += 12, 250, 9).plugTo(parPApp, "rotateX");
		cntrlFrame.cp5.addSlider("rotateY", 0, PApplet.TWO_PI, Controller.rotateYNbr, 10, y += 12, 250, 9).plugTo(parPApp, "rotateY");
		cntrlFrame.cp5.addSlider("rotateZ", 0, PApplet.TWO_PI, Controller.rotateZNbr, 10, y += 12, 250, 9).plugTo(parPApp, "rotateZ");
		cntrlFrame.cp5.setAutoInitialization(true);
	}
	*/
}
