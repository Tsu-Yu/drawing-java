package hierarchy;

//@Author Carrie Chang

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Picture extends PaintObject {
	private static final long serialVersionUID = 1L;

	// declare variable to get my picture path
	private String imagePath;
	private Image image;

	// from father
	public Picture(Point2D from, Point2D to, String path) {
		super(Color.BLACK, from, to);
		this.imagePath = path;
		this.image = new Image(imagePath);

	}

	// draw picture
	@Override
	public void draw(GraphicsContext gc) {
		double width = Math.abs(to.getX() - from.getX());
		double height = Math.abs(to.getY() - from.getY());
		double x = Math.min(from.getX(), to.getX());
		double y = Math.min(from.getY(), to.getY());

		gc.drawImage(image, x, y, width, height);
	}
}