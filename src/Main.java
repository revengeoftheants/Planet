import processing.core.*;
import processing.event.Event;
import controlP5.ControlP5;
import remixlab.proscene.*;

public class Main extends PApplet {

	/**
	 * Variables.
	 */
	PFont font;
	int latitudinalBandCnt = 280;  // Sphere detail setting. This is the number of latitudinal bands.
	static float rotationX = 0;
	static float rotationY = 0;
	float velocityX = 0;
	float velocityY = 0;
	//Controls myControls;
	Scene camScene;
	static float currRotateXRadNbr, currRotateYRadNbr, currRotateZRadNbr;
	float rotX, rotY, rotZ;

	float[] cx, cz, sphereX, sphereY, sphereZ;
	float sinLUT[];
	float cosLUT[];
	final float SINCOS_PRECISION = 0.5f;
	final int SINCOS_LENGTH = (int)(360.0 / SINCOS_PRECISION);
	final float GLOBE_RADIUS_PXL_NBR = 300;
	final int Z_AXIS_TRANS_NBR = -50;
	final float CAM_DSTNC_PXL_NBR = GLOBE_RADIUS_PXL_NBR * 2.5f;

	
	/**
	 * main method to run as a Java application outside Processing.
	 */
	public static void main(String args[]) {
		PApplet.main(new String[] { "Main" });
	}

	
	/**
	 * Setup.
	 */
	public void setup() {
		size(1200, 800, P3D);
		frameRate(20);
		font = loadFont("Helvetica-48.vlw");
		textMode(SHAPE);
		
		setupCamera();
	
		//myControls = new Controls(this);
	}
	

	/**
	 *  Draw loop.(non-Javadoc)
	 * @see processing.core.PApplet#draw()
	 */
	public void draw() {    
		background(245);
		smooth();
		drawSphere();
	}
	
	
	/**
	 * Set up the camera.
	 */
	private void setupCamera() {
		/*Peasycam setup.
		cam = new PeasyCam(this, width/2, height/2, Z_AXIS_TRANS_NBR, CAM_DSTNC_PXL_NBR);
		cam.setMinimumDistance(10);
		cam.setMaximumDistance(1000);
		cam.setSuppressRollRotationMode();
		*/
		
		// Proscene setup.
		camScene = new Scene(this);
		camScene.setGridIsDrawn(false);
		//camScene.setAxisIsDrawn(false);
		camScene.camera().frame().setSpinningFriction(0.7f);
		camScene.camera().frame().setTossingFriction(0.7f);
		camScene.setRadius(GLOBE_RADIUS_PXL_NBR * 1.2f);
		
		// We will use only the arcball camera profile.
		camScene.unregisterCameraProfile("FIRST_PERSON");
		camScene.unregisterCameraProfile("THIRD_PERSON");
		camScene.unregisterCameraProfile("WHEELED_ARCBALL");
		
		// Remove some functionality from our arcball camera profile because we don't want the user
		// to be able to do things such as move the camera.
		CameraProfile arcballProfile = camScene.getCameraProfiles()[0];
		arcballProfile.removeAllShortcuts();
		
		// The normal SHIFT+LEFT_ARROW action is to zoom, which we do not want.
		arcballProfile.setCameraMouseBinding(Event.SHIFT, LEFT, Scene.MouseAction.ROLL);
		// Currently middle and right mouse keys are not working in Proscene in Processing 2.
		arcballProfile.setCameraMouseBinding(Event.SHIFT, RIGHT, Scene.MouseAction.ROTATE);
		
		
		camScene.showAll();
	}

	
	/**
	 * Renders the globe.
	 */
	private void renderGlobe() {
		
		pushMatrix();

		
		strokeWeight(1);
		background(250);
		fill(0);
		
		drawSphere();
		popMatrix();
	}


	/**
	 * Not currently used...
	 * @param res
	 */
	private void initializeSphere(int res) {

		// Lookup tables. 720 entries in each.
		sinLUT = new float[SINCOS_LENGTH];
		cosLUT = new float[SINCOS_LENGTH];

		// Store the sine and cosine for each 0.5 degree increment.
		for (int i = 0; i < SINCOS_LENGTH; i++) {
			sinLUT[i] = (float) Math.sin(i * DEG_TO_RAD * SINCOS_PRECISION);
			cosLUT[i] = (float) Math.cos(i * DEG_TO_RAD * SINCOS_PRECISION);
		}

		float delta = (float)SINCOS_LENGTH/res; //Number of 0.5-degree increments per latitudinal band.
		float[] cx = new float[res];
		float[] cz = new float[res];

		// Calc unit circle in XZ plane
		println(SINCOS_LENGTH);
		for (int i = 0; i < res; i++) {
			cx[i] = -cosLUT[(int) (i*delta) % SINCOS_LENGTH];
			cz[i] = sinLUT[(int) (i*delta) % SINCOS_LENGTH];
		}

		// Computing vertexlist vertexlist starts at south pole
		int vertCount = res * (res-1) + 2;
		int currVert = 0;

		// Re-init arrays to store vertices
		sphereX = new float[vertCount];
		sphereY = new float[vertCount];
		sphereZ = new float[vertCount];
		float angle_step = (SINCOS_LENGTH*0.5f)/res;
		float angle = angle_step;

		// Step along Y axis
		for (int i = 1; i < res; i++) {
			float curradius = sinLUT[(int) angle % SINCOS_LENGTH];
			float currY = -cosLUT[(int) angle % SINCOS_LENGTH];
			for (int j = 0; j < res; j++) {
				sphereX[currVert] = cx[j] * curradius;
				sphereY[currVert] = currY;
				sphereZ[currVert++] = cz[j] * curradius;
			}
			angle += angle_step;
		}
		latitudinalBandCnt = res;
	}


	/**
	 * Draws the sphere.
	 */
	private void drawSphere() {
		float x, y, z;
		
		strokeWeight(1);
		stroke(165);
		
		// Rotate the X axis 90 degrees so that the poles point vertically.
		pushMatrix();
		rotateX(radians(90));
		
		for (int lng = 0; lng < 360; lng++) {	
			for(int lat = -90; lat < 90; lat++) {
				x = GLOBE_RADIUS_PXL_NBR * cos(radians(lat)) * cos(radians(lng));
				y = GLOBE_RADIUS_PXL_NBR * cos(radians(lat)) * sin(radians(lng));
				z = GLOBE_RADIUS_PXL_NBR * sin(radians(lat));
				point(x, y, z);
			}
		}
		
		// Draw the equator.
		ellipseMode(CENTER);
		noFill();
		ellipse(0, 0, GLOBE_RADIUS_PXL_NBR * 2, GLOBE_RADIUS_PXL_NBR * 2);
		
		popMatrix();
		
		// Draw the pole and prime meridian.
		line(0, -(GLOBE_RADIUS_PXL_NBR + GLOBE_RADIUS_PXL_NBR/10), 0, 0, (GLOBE_RADIUS_PXL_NBR + GLOBE_RADIUS_PXL_NBR/10), 0);
		ellipse(0, 0, GLOBE_RADIUS_PXL_NBR * 2, GLOBE_RADIUS_PXL_NBR * 2);
		

		textFont(font, 20);
		textAlign(CENTER);
		//stroke(50);
		fill(100);
		text("N", 0, -(GLOBE_RADIUS_PXL_NBR + GLOBE_RADIUS_PXL_NBR/8), 0);
	}
}
