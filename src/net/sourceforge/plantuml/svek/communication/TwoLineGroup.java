package net.sourceforge.plantuml.svek.communication;

import java.util.Objects;

import net.sourceforge.plantuml.svek.Line;

/**
 * A {@link LineGroup} of two lines, useful only for transitions between 1 and 3 line groups.
 *
 * @author Carlos Gomez
 */
class TwoLineGroup implements LineGroup {

  private final Line line1;
  private final Line line2;

  TwoLineGroup(final Line line1, final Line line2) {
    this.line1 = Objects.requireNonNull(line1);
    this.line2 = Objects.requireNonNull(line2);
  }

  @Override
  public LineGroup addLine(final Line line3) {
    return new ThreeLineGroup(this.line1, this.line2, line3);
  }

}
