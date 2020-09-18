package net.sourceforge.plantuml.communicationdiagram.link;

import java.util.UUID;

import net.sourceforge.plantuml.communicationdiagram.CommunicationDiagram;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkType;

/**
 * Service to transform the last {@link Link} of a communication diagram into a
 * {@link CommunicationLink}s.
 *
 * @author Carlos Gomez
 */
class LastLinkToCommunicationLinkTransformer {

  private final CommunicationLinkProperties linkProperties;
  private final CommunicationDiagram diagram;

  /**
   * Creates a new instance.
   *
   * @param diagram
   *        communication diagram where the links belong to
   * @param linkProperties
   *        properties that enable the transformation process by complementing and overriding the
   *        properties of the last link
   */
  LastLinkToCommunicationLinkTransformer(final CommunicationDiagram diagram,
      final CommunicationLinkProperties linkProperties) {
    this.linkProperties = linkProperties;
    this.diagram = diagram;
  }

  /**
   * Transforms the last {@link Link} of its communication diagram into a
   * {@link CommunicationLink}s.
   */
  void transform() {
    final Link nonCommunicationLink = this.diagram.removeLastLink();
    if (!tryToUpdateExistingLink(nonCommunicationLink)
        && !tryToAddEmptyAndOppositeLinks(nonCommunicationLink)) {
      addNewLink(nonCommunicationLink);
    }
  }

  private boolean tryToUpdateExistingLink(final Link nonCommunicationLink) {
    final CommunicationLink linkWithSameEnds = this.diagram.findLinkWithSameEnds(nonCommunicationLink);
    if (linkWithSameEnds != null) {
      this.diagram.replaceLink(linkWithSameEnds, linkWithSameEnds.withAppendedLabel(nonCommunicationLink.getLabel()));
      return true;
    }
    return false;
  }

  private boolean tryToAddEmptyAndOppositeLinks(final Link nonCommunicationLink) {
    final CommunicationLink linkWithOppositeEnds = this.diagram.findLinkWithOppositeEnds(nonCommunicationLink);
    if (linkWithOppositeEnds != null) {
      this.diagram.addLink(linkWithOppositeEnds.withLabel(Display.create(" ")));
      this.diagram.addLink(toCommunicationLink(nonCommunicationLink, linkWithOppositeEnds.getGroupId()));
      return true;
    }
    return false;
  }

  private void addNewLink(final Link nonCommunicationLink) {
    this.diagram.addLink(toCommunicationLink(nonCommunicationLink, UUID.randomUUID()));
  }

  private CommunicationLink toCommunicationLink(final Link nonCommunicationLink, final UUID linkGroupId) {
    LinkType linkType = nonCommunicationLink.getType()
        .withoutDecors1()
        .withoutDecors2();
    if (this.linkProperties.isDashed()) {
      linkType = linkType.goDashed();
    }

    int length = nonCommunicationLink.getLength();
    if (this.linkProperties.isVertical()) {
      length = Math.max(2, length);
    }

    final String arrowStyle = this.linkProperties.arrowStyle();
    return new CommunicationLink(nonCommunicationLink, linkGroupId, linkType, length, arrowStyle);
  }

}
