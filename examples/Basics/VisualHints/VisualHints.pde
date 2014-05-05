/**
 * Visual Hints.
 * by Jean Pierre Charalambos.
 * 
 * This illustrates how to customize the looking of proscene visual hints.
 * 
 * Press 'h' to display the key shortcuts and mouse bindings in the console.
 */

import remixlab.proscene.*;
import remixlab.dandelion.core.*;
import remixlab.dandelion.geom.*;

Scene scene;
boolean focusIFrame;
InteractiveAvatarFrame iFrame;
boolean displayPaths = true;

//Choose one of P3D for a 3D scene, or P2D or JAVA2D for a 2D scene
String renderer = P3D;

public void setup() {
  size(640, 360, renderer);
  scene = new CustomizedScene(this);
  iFrame = new InteractiveAvatarFrame(scene);
  iFrame.translate(new Vec(30, -30, 0));
  scene.keyboardAgent().profile().setShortcut('r', null);
  scene.setNonSeqTimers();
  scene.setVisualHints(Constants.AXIS | Constants.GRID | Constants.FRAME );
  //create a eye path and add some key frames:
  //key frames can be added at runtime with keys [j..n]
  scene.eye().setPosition(new Vec(80,0,0));
  if(scene.is3D()) scene.eye().lookAt( scene.eye().sceneCenter() );
  scene.eye().addKeyFrameToPath(1);

  scene.eye().setPosition(new Vec(30,30,-80));
  if(scene.is3D()) scene.eye().lookAt( scene.eye().sceneCenter() );
  scene.eye().addKeyFrameToPath(1);

  scene.eye().setPosition(new Vec(-30,-30,-80));
  if(scene.is3D()) scene.eye().lookAt( scene.eye().sceneCenter() );
  scene.eye().addKeyFrameToPath(1);

  scene.eye().setPosition(new Vec(-80,0,0));
  if(scene.is3D()) scene.eye().lookAt( scene.eye().sceneCenter() );
  scene.eye().addKeyFrameToPath(1);

  //re-position the eye:
  scene.eye().setPosition(new Vec(0,0,1));
  if(scene.is3D()) scene.eye().lookAt( scene.eye().sceneCenter() );
  scene.showAll();
}

public void draw() {
  background(40);
  fill(204, 102, 0, 150);
  scene.drawTorusSolenoid(2);

  // Save the current model view matrix
  pushMatrix();
  // Multiply matrix to get in the frame coordinate system.
  // applyMatrix(iFrame.matrix()) is possible but inefficient 
  iFrame.applyTransformation();//very efficient
  // Draw an axis using the Scene static function
  scene.drawAxes(20);

  // Draw a second box
  if (focusIFrame) {
    fill(0, 255, 255);
    scene.drawTorusSolenoid(6, 10);
  }
  else if (iFrame.grabsInput(scene.mouseAgent())) {
    fill(255, 0, 0);
    scene.drawTorusSolenoid(8, 10);
  }
  else {
    fill(0, 0, 255, 150);
    scene.drawTorusSolenoid(6, 10);
  }
  popMatrix();
  drawPaths();
}

public void keyPressed() {
  if ( key == 'i') {
    if ( focusIFrame ) {
      scene.mouseAgent().setDefaultGrabber(scene.eye().frame());
      scene.mouseAgent().enableTracking();
    } 
    else {
      scene.mouseAgent().setDefaultGrabber(iFrame);
      scene.mouseAgent().disableTracking();
    }
    focusIFrame = !focusIFrame;
  }
  if(key == 'u')
    displayPaths = !displayPaths;
}

public void drawPaths() {
  if(displayPaths) {
    pushStyle();
    colorMode(PApplet.RGB, 255);
    strokeWeight(3);
    stroke(220,0,220);
    scene.drawEyePaths();
    popStyle();
  }
  else
    scene.hideEyePaths();
}

public class CustomizedScene extends Scene {
  // We need to call super(p) to instantiate the base class
  public CustomizedScene(PApplet p) {
    super(p);
  }

  @Override
  protected void drawFramesHint() {
    pg().pushStyle();
    pg().colorMode(PApplet.RGB, 255);
    pg().strokeWeight(1);
    pg().stroke(0,220,0);
    drawFrameSelectionTargets();
    pg().popStyle();
  }
}
