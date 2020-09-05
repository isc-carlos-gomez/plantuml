package net.sourceforge.plantuml.communicationdiagram;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import net.sourceforge.plantuml.Dimension2DDouble;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.anim.Animation;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;
import net.sourceforge.plantuml.ugraphic.LimitFinder;
import net.sourceforge.plantuml.ugraphic.UGraphic2;
import net.sourceforge.plantuml.ugraphic.UTranslate;

/**
 * Customize {@link ImageBuilder} to support Communication Diagrams.
 * <p>
 * Line processing is slightly different in Communication Diagrams since lines can be grouped and
 * relocated to simulate communication links. These differences might cause an overflow in the
 * diagram boundaries and this class takes care of defining the diagram's dimensions and location
 * considering such overflow.
 *
 * @author Carlos Gomez
 */
class CommunicationImageBuilder extends ImageBuilder {

  private UDrawable udrawable;
  private final double margin1;
  private final double margin2;
  private Point2D.Double overflow;

  CommunicationImageBuilder(final ISkinParam skinParam, final double dpiFactor, final String metadata,
      final String warningOrError, final double margin1, final double margin2, final Animation animation,
      final HtmlColor backColor) {
    super(skinParam, dpiFactor, metadata, warningOrError, margin1, margin2, animation, backColor);
    this.margin1 = margin1;
    this.margin2 = margin2;
    this.overflow = new Point2D.Double();
  }

  @Override
  public void setUDrawable(final UDrawable udrawable) {
    super.setUDrawable(udrawable);
    this.udrawable = udrawable;
  }

  @Override
  public Dimension2D getFinalDimension(final StringBounder stringBounder) {
    final LimitFinder limitFinder = new LimitFinder(stringBounder, true);
    this.udrawable.drawU(limitFinder);
    final Dimension2D dim = new Dimension2DDouble(limitFinder.getMaxX(), limitFinder.getMaxY());

    this.overflow = new Point2D.Double(
        Math.abs(limitFinder.getMinX()),
        Math.abs(limitFinder.getMinY()));

    return new Dimension2DDouble(
        dim.getWidth() + 1 + this.margin1 + this.margin2 + this.overflow.x,
        dim.getHeight() + 1 + this.margin1 + this.margin2 + this.overflow.y);
  }

  @Override
  protected UGraphic2 createUGraphic(final FileFormatOption fileFormatOption, final long seed, final Dimension2D dim,
      final Animation animationArg, final double dx, final double dy) {
    final UGraphic2 uGraphic = super.createUGraphic(fileFormatOption, seed, dim, animationArg, dx, dy);
    return (UGraphic2) uGraphic.apply(new UTranslate(this.overflow));
  }

}
