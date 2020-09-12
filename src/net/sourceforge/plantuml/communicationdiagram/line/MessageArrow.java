package net.sourceforge.plantuml.communicationdiagram.line;

import java.awt.geom.Point2D;

import net.sourceforge.plantuml.Direction;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.color.HColorUtils;

/**
 * A drawable message arrow floating around a Communication Diagram Link.
 *
 * @author Carlos Gomez
 */
public class MessageArrow implements UDrawable {

  private final Point startPoint;
  private final Point endPoint;
  private final Direction direction;

  /**
   * Creates a new instance.
   *
   * @param startPoint
   *        the starting point
   * @param endPoint
   *        the end point
   * @param direction
   *        the arrow direction
   */
  MessageArrow(final Point startPoint, final Point endPoint, final Direction direction) {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    this.direction = direction;
  }

  @Override
  public void drawU(final UGraphic ug) {
    ug.apply(new UTranslate(this.startPoint.getX(), this.startPoint.getY()))
        .apply(HColorUtils.BLACK)
        .draw(new ULine(this.startPoint, this.endPoint));

    final Point2D arrowHeadPosition;
    if (this.direction == Direction.RIGHT || this.direction == Direction.DOWN) {
      arrowHeadPosition = this.endPoint;
    } else {
      arrowHeadPosition = this.startPoint;
    }

    LinkDecor.ARROW.getExtremityFactory(HColorUtils.BLACK)
        .createUDrawable(arrowHeadPosition, arrowHeadAngle(), null)
        .drawU(ug.apply(HColorUtils.BLACK));
  }

  private double arrowHeadAngle() {
    switch (this.direction) {
      case DOWN:
        return Math.PI / 2;
      case UP:
        return 3 * Math.PI / 2;
      case RIGHT:
        return 0;
      case LEFT:
        return Math.PI;
      default:
        throw new IllegalStateException("Unsupported direction: " + this.direction);
    }
  }

}
