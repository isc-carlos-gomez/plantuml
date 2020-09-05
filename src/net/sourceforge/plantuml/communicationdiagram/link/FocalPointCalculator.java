package net.sourceforge.plantuml.communicationdiagram.link;

import java.awt.geom.CubicCurve2D;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

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
        .map(bezier -> findBezierPoint(bezier, comparingX(x)));
  }

  private Optional<Point> findPointFromY(final double y) {
    return this.line.getDotPath().getBeziers()
        .stream()
        .filter(bezier -> bezier.y2 >= y)
        .findFirst()
        .map(bezier -> findBezierPoint(bezier, comparingY(y)));
  }

  private Point findBezierPoint(final CubicCurve2D.Double bezier, final Comparator<Point> comparator) {
    return IntStream.rangeClosed(0, 10)
        .mapToDouble(i -> i / 10.0)
        .mapToObj(t -> bezierPoint(bezier, t))
        .min(comparator)
        .orElseThrow(() -> new IllegalStateException("Expected Bezier point not found"));
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
    return Comparator.comparing(point -> Math.abs(x - point.getX()));
  }

  private static Comparator<Point> comparingY(final double y) {
    return Comparator.comparing(point -> Math.abs(y - point.getY()));
  }

}
