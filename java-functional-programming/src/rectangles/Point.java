package rectangles;

public class Point {
  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point() {
    this(0, 0);
  }

  public Point(int x) {
    this(x, 0);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Point setX(int newX) {
    if (newX < 0) {
      throw new IllegalArgumentException("Parameter must be non-negative");
    }
    return new Point(newX, y);
  }

  public Point setY(int newY) {
    if (newY < 0) {
      throw new IllegalArgumentException("Parameter must be non-negative");
    }
    return new Point(x, newY);
  }

  public boolean isLeftOf(Point other) {
    return (x < other.getX());
  }

  public boolean isRightOf(Point other) {
    return (x > other.getX());
  }

  public boolean isAbove(Point other) {
    return (y < other.getY());
  }

  public boolean isBelow(Point other) {
    return (y > other.getY());
  }

  public Point add(Point vector) {
    return new Point(x + vector.getX(), y + vector.getY());
  }
}
