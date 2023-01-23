package rectangles;

import java.util.Optional;

import static java.lang.Math.*;

public class Rectangle {

  private final int x1;
  private final int x2;
  private final int y1;
  private final int y2;

  public Rectangle(Point left, int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Width or height must be non-negative");
    }
    this.x1 = left.getX();
    this.y1 = left.getY();
    this.x2 = x1 + width;
    this.y2 = y1 + height;
  }

  public Rectangle(Point pointOne, Point pointTwo) {
    this(new Point(Math.min(pointOne.getX(), pointTwo.getX()), Math.min(pointOne.getY(), pointTwo.getY())),
            abs(pointOne.getX() - pointTwo.getX()), abs(pointOne.getY() - pointTwo.getY()));
  }

  public Rectangle(int width, int height) {
    this(new Point(0, 0), width, height);
  }

  public int getWidth() {
    return (x2 - x1);
  }

  public int getHeight() {
    return (y2 - y1);
  }

  public Point getTopLeft() {
    return new Point(x1, y1);
  }

  public Point getTopRight() {
    return new Point(x2, y1);
  }

  public Point getBottomLeft() {
    return new Point(x1, y2);
  }

  public Point getBottomRight() {
    return new Point(x2, y2);
  }

  public Rectangle setWidth(int newWidth) {
    if (newWidth < 0) {
      throw new IllegalArgumentException("Width must be non-negative");
    }
    return new Rectangle(this.getTopLeft(), newWidth, this.getHeight());
  }

  public Rectangle setHeight(int newHeight) {
    if (newHeight < 0) {
      throw new IllegalArgumentException("Height must be non-negative");
    }
    return new Rectangle(this.getTopLeft(), this.getWidth(), newHeight);
  }

  public int area() {
    return (this.getHeight() * this.getWidth());
  }

  public boolean contains(Point point) {
    int coordX = point.getX();
    int coordY = point.getY();
    return (x1 <= coordX && x2 >= coordX && y1 <= coordY && y2 >= coordY);
  }

  public boolean intersects(Rectangle other) {
    return (this.contains(other.getTopLeft()) || this.contains(other.getBottomLeft()) ||
            this.contains(other.getTopRight()) || this.contains(other.getBottomRight()));
  }

  public Optional<Rectangle> intersection(Rectangle other) {
    if (!this.intersects(other)) {
      return Optional.empty();
    } else {
      Point topLeft = other.getTopLeft();
      Point bottomRight = other.getBottomRight();
      int x3 = max(x1, topLeft.getX());
      int y3 = max(y1, topLeft.getY());
      int x4 = min(x2, bottomRight.getX());
      int y4 = min(y2, bottomRight.getY());
      return Optional.of(new Rectangle(new Point(x3, y3), new Point(x4, y4)));
    }
  }
}
