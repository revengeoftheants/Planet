import processing.core.*;
import controlP5.*;

/*
 * @author Kevin Dean
 * Based on Andreas Schlegel, 2012
 * http://www.sojamo.de/libraries/controlP5/examples/extra/ControlP5frame/ControlP5frame.pde
 */
public class ControlFrame extends PApplet {

	/*
	 * Constants.
	 */
	final int BACKGRND_CLR_NBR = 0;
	final int CNTRL_X_COORD_STRT_NBR = 10;
	final int CNTRL_Y_COORD_STRT_OFFSET_NBR = 12;
	final int CNTRL_WDTH_NBR = 250;
	final int CNTRL_HGHT_NBR = 9;


	/*
	 * Global variables
	 */
	PApplet parPApp = null;
	ControlP5 cp5;
	int wdthNbr, hghtNbr;
	RadioButton thisRadioButton;


	/*
	 * Constructor
	 */
	public ControlFrame(PApplet inpParPApp, int inpWdthNbr, int inpHghtNbr) {
		parPApp = inpParPApp;
		wdthNbr = inpWdthNbr;
		hghtNbr = inpHghtNbr;
	}


	/*
	 * Retrieves the control.
	 */
	public ControlP5 rtrvCntrl() {
		return cp5;
	}


	/*
	 * (non-Javadoc)
	 * @see processing.core.PApplet#setup()
	 * 
	 * The ControlFrame class extends PApplet, so we are creating a new Processing
	 * applet inside a new frame, and it contains a ControlP5 object.
	 */
	public void setup() {
		size(wdthNbr, hghtNbr);
		frameRate(25);
		cp5 = new ControlP5(this);

		int y = 0;
		//cp5.addSlider("rotateX", 0, 360, PlanetDrawer.rotX, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "rotX");
		//cp5.addSlider("rotateY", 0, 360, PlanetDrawer.rotY, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "rotY");
		//cp5.addSlider("rotateZ", 0, 360, PlanetDrawer.rotZ, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "rotZ");
		cp5.setAutoInitialization(true);
	}


	public void draw() {
		background(BACKGRND_CLR_NBR);
	}

	public void keyPressed() {
		switch(key) {
		case('0'): thisRadioButton.deactivateAll(); break;
		case('1'): thisRadioButton.activate(0); break;
		case('2'): thisRadioButton.activate(1); break;
		case('3'): thisRadioButton.activate(3); break;
		}

	}

	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isFrom(thisRadioButton)) {
			print("got an event from " + theEvent.getName() + "\t");
		}
	}
}
