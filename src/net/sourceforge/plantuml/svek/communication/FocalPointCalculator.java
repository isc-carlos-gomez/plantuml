package net.sourceforge.plantuml.svek.communication;

import java.awt.geom.CubicCurve2D;
import java.util.Optional;

import net.sourceforge.plantuml.svek.Line;

/**
 * Service used by {@link LineGroup} to calculate the focal point of a line, i.e. the point where
 * the center of a label perpendicularly intercepts a line.
 *
 * @author Carlos Gomez
 */
class FocalPointCalculator {

  private final Line line;
  private final Orientation orientation;

  /**
   * Creates a new calculator.
   *
   * @param line
   *        the line to be analyzed
   * @param orientation
   *        the orientation of the {@link LineGroup}
   */
  FocalPointCalculator(final Line line, final Orientation orientation) {
    this.line = line;
    this.orientation = orientation;
  }

  /**
   * @return the focal point of the line analyzed by this calculator
   */
  Point calculate() {
    if (this.orientation == Orientation.HORIZONTAL) {
      final double x = this.line.messageBox().center().getX();
      return findPointFromX(x)
          .orElseThrow(() -> new IllegalStateException("Expected focal point not found"));
    }
    final double y = this.line.messageBox().center().getY();
    return findPointFromY(y)
        .orElseThrow(() -> new IllegalStateException("Expected focal point not found"));
  }

  private Optional<Point> findPointFromX(final double x) {
    return this.line.getDotPath().getBeziers()
        .stream()
        .filter(bezier -> bezier.x2 >= x)
        .findFirst()
        .map(bezier -> findBezierPointFromX(bezier, x));
  }

  private Optional<Point> findPointFromY(final double y) {
    return this.line.getDotPath().getBeziers()
        .stream()
        .filter(bezier -> bezier.y2 >= y)
        .findFirst()
        .map(bezier -> findBezierPointFromY(bezier, y));
  }

  private Point findBezierPointFromX(final CubicCurve2D.Double bezier, final double x) {
    final double threshold = 5;
    double t = 0.5;
    Point point = bezierPoint(bezier, t);
    int limit = 20;
    while (Math.abs(point.x - x) > threshold && limit > 0) {
      if (point.x > x) {
        t = t - t / 2;
      } else {
        t = t + t / 2;
      }
      point = bezierPoint(bezier, t);
      limit--;
    }
    return point;
  }

  private Point findBezierPointFromY(final CubicCurve2D.Double bezier, final double y) {
    final double threshold = 5;
    double t = 0.5;
    Point point = bezierPoint(bezier, t);
    int limit = 0;
    while (Math.abs(point.y - y) > threshold && limit > 0) {
      if (point.y > y) {
        t = t - t / 2;
      } else {
        t = t + t / 2;
      }
      point = bezierPoint(bezier, t);
      limit--;
    }
    return point;
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

}
