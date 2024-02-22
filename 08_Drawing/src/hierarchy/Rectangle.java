package hierarchy;
//@Author Carrie Chang

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends PaintObject {
	private static final long serialVersionUID = 1L;

	// from father
	public Rectangle(Color color, Point2D from, Point2D to) {
		super(color, from, to);

	}

	// draw rectangle
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.setStroke(color);

		double width = Math.abs(to.getX() - from.getX());
		double height = Math.abs(to.getY() - from.getY());

		double x = Math.min(from.getX(), to.getX());
		double y = Math.min(from.getY(), to.getY());

		gc.fillRect(x, y, width, height);
	}
}