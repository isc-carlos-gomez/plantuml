package net.sourceforge.plantuml.communicationdiagram.line;

import java.awt.geom.Point2D;
import java.util.Objects;

import net.sourceforge.plantuml.Direction;

/**
 * Service to build a {@link MessageArrow} that corresponds with the position of a line message.
 *
 * @author Carlos Gomez
 */
class MessageArrowBuilder {

  private static final double ARROW_LENGTH = 20;
  private static final int LINK_CENTER_GAP = 10;

  private Point2D lineStart;
  private Point2D lineEnd;
  private Rectangle lineMessageBox;
  private boolean lineInverted;
  private Orientation linkOrientation;
  private Point linkFocalPoint;

  MessageArrowBuilder withLineStart(final Point2D lineStart) {
    this.lineStart = lineStart;
    return this;
  }

  MessageArrowBuilder withLineEnd(final Point2D lineEnd) {
    this.lineEnd = lineEnd;
    return this;
  }

  MessageArrowBuilder withLineMessageBox(final Rectangle lineMessageBox) {
    this.lineMessageBox = lineMessageBox;
    return this;
  }

  MessageArrowBuilder withLineInverted(final boolean lineInverted) {
    this.lineInverted = lineInverted;
    return this;
  }

  MessageArrowBuilder withLinkOrientation(final Orientation linkOrientation) {
    this.linkOrientation = linkOrientation;
    return this;
  }

  MessageArrowBuilder withLinkFocalPoint(final Point linkFocalPoint) {
    this.linkFocalPoint = linkFocalPoint;
    return this;
  }

  /**
   * @return a new {@link MessageArrow}
   */
  MessageArrow build() {
    checkAllPropertiesPresent();
    final Direction direction = calculateDirection();
    final Point startPoint = calculateStartPoint();
    final Point endPoint = calculateEndPoint(startPoint);
    return new MessageArrow(startPoint, endPoint, direction);
  }

  private void checkAllPropertiesPresent() {
    Objects.requireNonNull(this.lineStart);
    Objects.requireNonNull(this.lineEnd);
    Objects.requireNonNull(this.lineMessageBox);
    Objects.requireNonNull(this.lineInverted);
    Objects.requireNonNull(this.linkOrientation);
    Objects.requireNonNull(this.linkFocalPoint);
  }

  private Direction calculateDirection() {
    final Direction direction = naturalDirection();
    if (this.lineInverted) {
      return direction.getInv();
    }
    return direction;
  }

  private Direction naturalDirection() {
    final double angle =
        Math.atan2(-this.lineEnd.getY() + this.lineStart.getY(), this.lineEnd.getX() - this.lineStart.getX());
    if (this.linkOrientation == Orientation.HORIZONTAL) {
      if (Math.abs(angle) <= Math.PI / 2) {
        return Direction.RIGHT;
      }
      return Direction.LEFT;
    }
    if (angle >= 0) {
      return Direction.UP;
    }
    return Direction.DOWN;
  }

  private Point calculateStartPoint() {
    if (this.linkOrientation == Orientation.HORIZONTAL) {
      return calculateHorizontalStartPoint();
    }
    return calculateVerticalStartPoint();
  }

  private Point calculateHorizontalStartPoint() {
    final double labelCenter = this.lineMessageBox.center().getX();
    final double x = labelCenter - ARROW_LENGTH / 2;
    double y = this.linkFocalPoint.getY();
    if (this.lineMessageBox.topLeft().getY() < y) {
      y -= LINK_CENTER_GAP;
    } else {
      y += LINK_CENTER_GAP;
    }
    return new Point(x, y);
  }

  private Point calculateVerticalStartPoint() {
    final double labelCenter = this.lineMessageBox.center().getY();
    final double y = labelCenter - ARROW_LENGTH / 2;
    double x = this.linkFocalPoint.getX();
    if (this.lineMessageBox.topLeft().getX() < x) {
      x -= LINK_CENTER_GAP;
    } else {
      x += LINK_CENTER_GAP;
    }
    return new Point(x, y);
  }

  private Point calculateEndPoint(final Point startPoint) {
    if (this.linkOrientation == Orientation.HORIZONTAL) {
      return new Point(startPoint.getX() + ARROW_LENGTH, startPoint.getY());
    }
    return new Point(startPoint.getX(), startPoint.getY() + ARROW_LENGTH);
  }

}
