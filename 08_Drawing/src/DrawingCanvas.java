import java.util.Vector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * A GUI for NetPaint that has all PaintObjects drawn on it. This file also
 * represents the controller as it controls how paint objects are drawn and
 * sends new paint objects to the server. All Client objects also listen to the
 * server to read the Vector of paint objects and repaint every time any client
 * adds a new one.
 * 
 * @author Rick Mercer
 * 
 */
public class DrawingCanvas extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  // Use Vector instead of ArrayList
  private Vector<PaintObject> allPaintObjects;

  enum CurrentPaintObject {
    LINE, RECTANGLE, OVAL, PICTURE
  }

  private CurrentPaintObject currentShape = CurrentPaintObject.LINE;
  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane all = new BorderPane();
    
    // Put the drawing pane, a Canvas, into the center
    Canvas canvas = new Canvas(960, 740);
    setMouseHandlersOn(canvas);
    all.setCenter(canvas);

    // You will need to call allPaintObjects after you clear the Canvas with fillRect.
    // At that point, draw all shapes and then draw the shape being created with 
    // mouse event handlers while the current shape is being drawn at each mouse move.
    allPaintObjects = new Vector<PaintObject>();
  
    Scene scene = new Scene(all, 960, 740);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void setMouseHandlersOn(Canvas canvas) {
     // TODO: You need to handle MouseClick and MousMove events.
  }

}