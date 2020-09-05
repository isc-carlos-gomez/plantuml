package net.sourceforge.plantuml.communicationdiagram;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.Pragma;
import net.sourceforge.plantuml.communicationdiagram.link.CommunicationLink;
import net.sourceforge.plantuml.core.UmlSource;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.dot.DotData;
import net.sourceforge.plantuml.cucadiagram.entity.EntityFactory;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.svek.DotStringFactory;
import net.sourceforge.plantuml.svek.GeneralImageBuilder;
import net.sourceforge.plantuml.svek.Line;

/**
 * Customize {@link GeneralImageBuilder} to support Communication Diagrams by creating communication
 * lines instead of regular {@link Line}s.
 *
 * @author Carlos Gomez
 */
class CommunicationGeneralImageBuilder extends GeneralImageBuilder {

  private final Map<UUID, CommunicationLink> linkGroupIdToCommunicationLink;

  CommunicationGeneralImageBuilder(final DotData dotData, final EntityFactory entityFactory,
      final UmlSource source, final Pragma pragma, final StringBounder stringBounder) {
    super(dotData, entityFactory, source, pragma, stringBounder);
    this.linkGroupIdToCommunicationLink = new HashMap<>();
  }

  @Override
  protected Line newLine(final Link link, final ISkinParam skinParam, final FontConfiguration labelFont,
      final DotStringFactory dotStringFactory, final StringBounder stringBounder, final DotData dotData) {
    final CommunicationLink communicationLink = this.linkGroupIdToCommunicationLink.computeIfAbsent(
        ((net.sourceforge.plantuml.communicationdiagram.CommunicationLink) link).getGroupId(), CommunicationLink::new);

    final Line line = new Line(link, dotStringFactory.getColorSequence(), skinParam, stringBounder,
        labelFont, dotStringFactory.getBibliotekon(), dotData.getPragma(), communicationLink);
    communicationLink.addLine(line);

    return line;

  }

}
