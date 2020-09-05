package net.sourceforge.plantuml.communicationdiagram.line;

import net.sourceforge.plantuml.posimo.Positionable;
import net.sourceforge.plantuml.ugraphic.MinMax;

/**
 * Value object representing a rectangular area drawn in a Communication Diagram.
 *
 * @author Carlos Gomez
 */
public class Rectangle {

  private static final int NO_OVERLAP = 0;
  private static final int FULL_OVERLAP = 1;

  private final double x1;
  private final double y1;
  private final double x2;
  private final double y2;

  /**
   * Creates a new rectangle from the coordinates of two points representing the top left and the
   * bottom right corners.
   *
   * @param x1
   *        x coordinate of the top left corner
   * @param y1
   *        y coordinate of the top left corner
   * @param x2
   *        x coordinate of the bottom right corner
   * @param y2
   *        y coordinate of the bottom right corner
   */
  public Rectangle(final double x1, final double y1, final double x2, final double y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  /**
   * Creates a new rectangle from a {@link Positionable} object.
   *
   * @param positionable
   *        the positionable object
   */
  public Rectangle(final Positionable positionable) {
    this(
        positionable.getPosition().getX(),
        positionable.getPosition().getY(),
        positionable.getPosition().getX() + positionable.getSize().getWidth(),
        positionable.getPosition().getY() + positionable.getSize().getHeight());
  }

  /**
   * Creates a new rectangle from a {@link MinMax} object.
   *
   * @param minMax
   *        the {@link MinMax} object
   */
  public Rectangle(final MinMax minMax) {
    this(
        minMax.getMinX(),
        minMax.getMinY(),
        minMax.getMaxX(),
        minMax.getMaxY());
  }

  /**
   * @param other
   *        the other rectangle to compare
   * @return the overlap between this and one other rectangle
   */
  public Overlap overlap(final Rectangle other) {
    final double horizontalOverlap = horizontalOverlap(other);
    final double verticalOverlap = verticalOverlap(other);

    if (horizontalOverlap == NO_OVERLAP && verticalOverlap == NO_OVERLAP) {
      return Overlap.NONE;
    }
    if (horizontalOverlap == verticalOverlap) {
      return largerOverlap(other);
    }
    if (horizontalOverlap > verticalOverlap) {
      return Overlap.HORIZONTAL;
    }
    return Overlap.VERTICAL;
  }

  /**
   * @param deltaX
   *        the delta in the X coordinate
   * @param deltaY
   *        the delta in the Y coordinate
   * @return a new rectangle with the coordinates of this rectangle moved a delta value
   */
  public Rectangle withDelta(final double deltaX, final double deltaY) {
    return new Rectangle(this.x1 + deltaX, this.y1 + deltaY, this.x2 + deltaX, this.y2 + deltaY);
  }

  /**
   * @return the top left corner of this rectangle
   */
  public Point topLeft() {
    return new Point(this.x1, this.y1);
  }

  /**
   * @return the central point of this rectangle
   */
  public Point center() {
    return new Point(
        this.x1 + width() / 2,
        this.y1 + height() / 2);
  }

  /**
   * @return the width of this rectangle
   */
  public double width() {
    return Math.abs(this.x1 - this.x2);
  }

  /**
   * @return the height of this rectangle
   */
  public double height() {
    return Math.abs(this.y1 - this.y2);
  }

  /**
   * @param other
   *        the rectangle to join
   * @return a new rectangle representing the joined area of this and one other rectangle
   */
  public Rectangle join(final Rectangle other) {
    return new Rectangle(
        Math.min(this.x1, other.x1),
        Math.min(this.y1, other.y1),
        Math.max(this.x2, other.x2),
        Math.max(this.y2, other.y2));
  }

  private double horizontalOverlap(final Rectangle other) {
    final Rectangle rectangle1;
    final Rectangle rectangle2;
    if (this.x1 < other.x1) {
      rectangle1 = this;
      rectangle2 = other;
    } else {
      rectangle1 = other;
      rectangle2 = this;
    }
    if (rectangle1.x2 < rectangle2.x1) {
      return NO_OVERLAP;
    }
    if (rectangle1.x2 >= rectangle2.x2) {
      return FULL_OVERLAP;
    }
    return (rectangle1.x2 - rectangle2.x1) / rectangle2.width();
  }

  private double verticalOverlap(final Rectangle other) {
    final Rectangle rectangle1;
    final Rectangle rectangle2;
    if (this.y1 < other.y1) {
      rectangle1 = this;
      rectangle2 = other;
    } else {
      rectangle1 = other;
      rectangle2 = this;
    }
    if (rectangle1.y2 < rectangle2.y1) {
      return NO_OVERLAP;
    }
    if (rectangle1.y2 >= rectangle2.y2) {
      return FULL_OVERLAP;
    }
    return (rectangle1.y2 - rectangle2.y1) / rectangle2.height();
  }

  private Overlap largerOverlap(final Rectangle other) {
    if (Math.min(width(), other.width()) > Math.min(height(), other.height())) {
      return Overlap.HORIZONTAL;
    }
    return Overlap.VERTICAL;
  }

  /**
   * Enumeration of the overlaps that can be found between two rectangles.
   *
   * @author Carlos Gomez
   */
  enum Overlap {
    HORIZONTAL, VERTICAL, NONE;
  }

}
