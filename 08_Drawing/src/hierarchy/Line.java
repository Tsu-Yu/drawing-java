package hierarchy;

// @Author Carrie Chang

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends PaintObject {
	private static final long serialVersionUID = 1L;

	// from father
	public Line(Color color, Point2D from, Point2D to) {
		super(color, from, to);

	}

	// draw line
	@Override
	public void draw(GraphicsContext gc) {
		gc.setStroke(color);
		gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
	}
}
