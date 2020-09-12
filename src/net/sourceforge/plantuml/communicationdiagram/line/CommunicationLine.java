package net.sourceforge.plantuml.communicationdiagram.line;

import java.util.List;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.Pragma;
import net.sourceforge.plantuml.cucadiagram.IGroup;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.color.Colors;
import net.sourceforge.plantuml.posimo.DotPath;
import net.sourceforge.plantuml.svek.Bibliotekon;
import net.sourceforge.plantuml.svek.Cluster;
import net.sourceforge.plantuml.svek.ColorSequence;
import net.sourceforge.plantuml.svek.Line;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.color.HColor;

/**
 * A {@link Line} for a Communication Diagram.
 *
 * @author Carlos Gomez
 */
public class CommunicationLine extends Line {

  private final CommunicationLineGroup group;
  private final Link link;
  private final Bibliotekon bibliotekon;

  private CommunicationLine(final Builder builder) {
    super(builder.link, builder.colorSequence, builder.skinParam, builder.stringBounder, builder.labelFont,
        builder.bibliotekon, builder.pragma);
    this.group = builder.group;
    this.link = builder.link;
    this.bibliotekon = builder.bibliotekon;
  }

  @Override
  protected void drawRainbow(final UGraphic ug, final HColor color, final DotPath todraw,
      final List<Colors> supplementaryColors, final UStroke stroke) {
    if (this.group.isLineVisible(this)) {
      super.drawRainbow(ug.apply(stroke.onlyThickness()), color, todraw, supplementaryColors, stroke);
    }
  }

  /**
   * @return the rectangular area occupied by the message label of this line
   */
  Rectangle messageBox() {
    double x = 0;
    double y = 0;
    if (this.link.isAutoLinkOfAGroup()) {
      final Cluster cl = this.bibliotekon.getCluster((IGroup) this.link.getEntity1());
      if (cl != null) {
        x += cl.getWidth();
        x -= getDotPath().getStartPoint().getX() - cl.getMinX();
      }
    }
    x += getDx();
    y += getDy();
    return new Rectangle(getLabelXY()).withDelta(x, y);
  }

  /**
   * Creates builder to build {@link CommunicationLine}.
   *
   * @return created builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder to build {@link CommunicationLine}.
   */
  public static final class Builder {
    private Link link;
    private ColorSequence colorSequence;
    private ISkinParam skinParam;
    private StringBounder stringBounder;
    private FontConfiguration labelFont;
    private Bibliotekon bibliotekon;
    private Pragma pragma;
    private CommunicationLineGroup group;

    public Builder withLink(final Link link) {
      this.link = link;
      return this;
    }

    public Builder withColorSequence(final ColorSequence colorSequence) {
      this.colorSequence = colorSequence;
      return this;
    }

    public Builder withSkinParam(final ISkinParam skinParam) {
      this.skinParam = skinParam;
      return this;
    }

    public Builder withStringBounder(final StringBounder stringBounder) {
      this.stringBounder = stringBounder;
      return this;
    }

    public Builder withLabelFont(final FontConfiguration labelFont) {
      this.labelFont = labelFont;
      return this;
    }

    public Builder withBibliotekon(final Bibliotekon bibliotekon) {
      this.bibliotekon = bibliotekon;
      return this;
    }

    public Builder withPragma(final Pragma pragma) {
      this.pragma = pragma;
      return this;
    }

    public Builder withGroup(final CommunicationLineGroup group) {
      this.group = group;
      return this;
    }

    public CommunicationLine build() {
      return new CommunicationLine(this);
    }
  }

}
