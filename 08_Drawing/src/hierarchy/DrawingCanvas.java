package hierarchy;

import java.util.Vector;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * A GUI for NetPaint that has all PaintObjects drawn on it. This file also
 * represents the controller as it controls how paint objects are drawn and
 * sends new paint objects to the server. All Client objects also listen to the
 * server to read the Vector of paint objects and repaint every time any client
 * adds a new one.
 * 
 * @author Rick Mercer and Carrie Chang
 * 
 */
public class DrawingCanvas extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	// Use Vector instead of ArrayList
	private Vector<PaintObject> allPaintObjects;

	enum CurrentPaintObject {
		LINE, RECTANGLE, OVAL, PICTURE;

		// for color picker to set color
		private Color color;

		public void setColor(Color color) {
			this.color = color;
		}

		public Color getColor() {
			return color;
		}
	}

	private CurrentPaintObject currentShape = CurrentPaintObject.LINE;

	// color picker component
	private ColorPicker colorPicker;

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane all = new BorderPane();

		// Put the drawing pane, a Canvas, into the center
		Canvas canvas = new Canvas(960, 700);
		setMouseHandlersOn(canvas);
		all.setCenter(canvas);

		// You will need to call allPaintObjects after you clear the Canvas with
		// fillRect.
		// At that point, draw all shapes and then draw the shape being created with
		// mouse event handlers while the current shape is being drawn at each mouse
		// move.
		allPaintObjects = new Vector<PaintObject>();
		drawAllPaintObects(allPaintObjects, canvas);

		// choose color from color picker
		colorPicker = new ColorPicker();
		colorPicker.setValue(Color.BLACK);
		colorPicker.setOnAction(event -> {
			Color selectedColor = colorPicker.getValue();
			updateCurrentShapeColor(selectedColor);
		});

		// bottom box for all tools
		HBox controlPanel = new HBox();
		controlPanel.setPadding(new Insets(10));
		controlPanel.setSpacing(10);
		controlPanel.setAlignment(Pos.CENTER);

		ToggleGroup shapeToggleGroup = new ToggleGroup();

		// draw line tool
		RadioButton line = new RadioButton("Line");
		line.setToggleGroup(shapeToggleGroup);
		line.setSelected(true);
		line.setOnAction(event -> currentShape = CurrentPaintObject.LINE);

		// draw rectangle tool
		RadioButton rectangle = new RadioButton("Rectangle");
		rectangle.setToggleGroup(shapeToggleGroup);
		rectangle.setOnAction(event -> currentShape = CurrentPaintObject.RECTANGLE);

		// draw oval tool
		RadioButton oval = new RadioButton("Oval");
		oval.setToggleGroup(shapeToggleGroup);
		oval.setOnAction(event -> currentShape = CurrentPaintObject.OVAL);

		// draw picture tool
		RadioButton picture = new RadioButton("Picture");
		picture.setToggleGroup(shapeToggleGroup);
		picture.setOnAction(event -> currentShape = CurrentPaintObject.PICTURE);

		// set all tools to bottom control panel
		controlPanel.getChildren().addAll(line, rectangle, oval, picture, colorPicker);
		all.setBottom(controlPanel);

		// display window
		Scene scene = new Scene(all, 960, 740);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// set background to be white and draw all object
	private void drawAllPaintObects(Vector<PaintObject> allPaintObjects, Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (PaintObject po : allPaintObjects) {
			po.draw(gc);
		}
	}

	// update current shape color
	private void updateCurrentShapeColor(Color selectedColor) {
		if (currentShape != null) {
			currentShape.setColor(selectedColor);
		}

	}

	// control all mouse action
	private void setMouseHandlersOn(Canvas canvas) {
		canvas.setOnMousePressed(event -> {
			double x = event.getX();
			double y = event.getY();

			// change shape to what i choose
			switch (currentShape) {
			case LINE:
				allPaintObjects.add(new Line(colorPicker.getValue(), new Point2D(x, y), new Point2D(x, y)));
				break;
			case RECTANGLE:
				allPaintObjects.add(new Rectangle(colorPicker.getValue(), new Point2D(x, y), new Point2D(x, y)));
				break;
			case OVAL:
				allPaintObjects.add(new Oval(colorPicker.getValue(), new Point2D(x, y), new Point2D(x, y)));
				break;
			case PICTURE:
				allPaintObjects.add(new Picture(new Point2D(x, y), new Point2D(x, y),
						"file:///Users/carriechang/Documents/gitrepos/drawing-Tsu-Yu/08_Drawing/src/hierarchy/doge.jpeg"));
				break;
			}
			redrawCanvas(canvas);
		});

		// let my shape done when i drag mouse
		canvas.setOnMouseDragged(event -> {
			if (!allPaintObjects.isEmpty()) {
				PaintObject currentObject = allPaintObjects.lastElement();
				if (currentObject instanceof Line || currentObject instanceof Rectangle || currentObject instanceof Oval
						|| currentObject instanceof Picture) {
					currentObject.to = new Point2D(event.getX(), event.getY());
					redrawCanvas(canvas);
				}
			}
		});
		canvas.setOnMouseReleased(event -> {
		});
	}

	// redraw canvas method
	private void redrawCanvas(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (PaintObject paintObject : allPaintObjects) {
			paintObject.draw(gc);
		}
	}

}