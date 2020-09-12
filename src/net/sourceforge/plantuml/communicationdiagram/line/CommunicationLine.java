package net.sourceforge.plantuml.communicationdiagram.line;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.Pragma;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.svek.Bibliotekon;
import net.sourceforge.plantuml.svek.ColorSequence;
import net.sourceforge.plantuml.svek.Line;

/**
 * A {@link Line} for a Communication Diagram.
 *
 * @author Carlos Gomez
 */
public class CommunicationLine extends Line {

  public CommunicationLine(final Link link, final ColorSequence colorSequence, final ISkinParam skinParam,
      final StringBounder stringBounder,
      final FontConfiguration font, final Bibliotekon bibliotekon, final Pragma pragma) {
    super(link, colorSequence, skinParam, stringBounder, font, bibliotekon, pragma);
  }

}
