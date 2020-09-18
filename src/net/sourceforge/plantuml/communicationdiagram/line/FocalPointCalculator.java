package net.sourceforge.plantuml.communicationdiagram.line;

import java.awt.geom.CubicCurve2D;
import java.util.Comparator;

/**
 * Service used by {@link LineGroup} to calculate the focal point of a line, i.e. the point where
 * the center of a label perpendicularly intercepts a line.
 *
 * @author Carlos Gomez
 */
class FocalPointCalculator {

  private final CommunicationLine line;
  private final Orientation orientation;

  /**
   * Creates a new calculator.
   *
   * @param line
   *        the line to be analyzed
   * @param orientation
   *        the orientation of the {@link LineGroup}
   */
  FocalPointCalculator(final CommunicationLine line, final Orientation orientation) {
    this.line = line;
    this.orientation = orientation;
  }

  /**
   * @return the focal point of the line analyzed by this calculator
   */
  Point calculate() {
    if (this.orientation == Orientation.HORIZONTAL) {
      final double x = this.line.messageBox().center().getX();
      return findPointFromX(x);
    }
    final double y = this.line.messageBox().center().getY();
    return findPointFromY(y);
  }

  private Point findPointFromX(final double x) {
    return findBezierPoint(findBezierCurveAtX(x), comparingX(x));
  }

  private Point findPointFromY(final double y) {
    return findBezierPoint(findBezierCurveAtY(y), comparingY(y));
  }

  private CubicCurve2D.Double findBezierCurveAtX(final double x) {
    for (final CubicCurve2D.Double bezier : this.line.getDotPath().getBeziers()) {
      if (bezier.x2 >= x) {
        return bezier;
      }
    }
    throw new IllegalStateException("Expected bezier curve not found");
  }

  private CubicCurve2D.Double findBezierCurveAtY(final double y) {
    for (final CubicCurve2D.Double bezier : this.line.getDotPath().getBeziers()) {
      if (bezier.y2 >= y) {
        return bezier;
      }
    }
    throw new IllegalStateException("Expected bezier curve not found");
  }

  private Point findBezierPoint(final CubicCurve2D.Double bezier, final Comparator<Point> comparator) {
    Point foundPoint = bezierPoint(bezier, 0);
    for (int i = 1; i <= 10; i++) {
      final double time = i / 10.0;
      final Point bezierPoint = bezierPoint(bezier, time);
      if (comparator.compare(foundPoint, bezierPoint) > 0) {
        foundPoint = bezierPoint;
      }
    }
    return foundPoint;
  }

  private Point bezierPoint(final CubicCurve2D.Double bezier, final double t) {
    final double x = bezierEquation(bezier.x1, bezier.ctrlx1, bezier.ctrlx2, bezier.x2, t);
    final double y = bezierEquation(bezier.y1, bezier.ctrly1, bezier.ctrly2, bezier.y2, t);
    return new Point(x, y);
  }

  private double bezierEquation(final double a0, final double a1, final double a2, final double a3, final double t) {
    return Math.pow(1 - t, 3) * a0 + 3 * Math.pow(1 - t, 2) * t * a1 + 3 * (1 - t) * Math.pow(t, 2) * a2
        + Math.pow(t, 3) * a3;
  }

  private static Comparator<Point> comparingX(final double x) {
    return new Comparator<Point>() {

      @Override
      public int compare(final Point point1, final Point point2) {
        return Double.compare(
            Math.abs(x - point1.getX()),
            Math.abs(x - point2.getX()));
      }
    };
  }

  private static Comparator<Point> comparingY(final double y) {
    return new Comparator<Point>() {

      @Override
      public int compare(final Point point1, final Point point2) {
        return Double.compare(
            Math.abs(y - point1.getY()),
            Math.abs(y - point2.getY()));
      }
    };
  }

}
