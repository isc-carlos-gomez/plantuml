package net.sourceforge.plantuml.communicationdiagram;

import java.util.Optional;
import java.util.UUID;

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
    tryToUpdateExistingLink(nonCommunicationLink)
        .or(() -> tryToAddEmptyAndOppositeLinks(nonCommunicationLink))
        .orElse(addNewLink(nonCommunicationLink))
        .run();
  }

  private Optional<Runnable> tryToUpdateExistingLink(final Link nonCommunicationLink) {
    return this.diagram.findLinkWithSameEnds(nonCommunicationLink)
        .map(link -> () -> {
          this.diagram.replaceLink(link, link.withAppendedLabel(nonCommunicationLink.getLabel()));
        });
  }

  private Optional<Runnable> tryToAddEmptyAndOppositeLinks(final Link nonCommunicationLink) {
    return this.diagram.findLinkWithOppositeEnds(nonCommunicationLink)
        .map(link -> () -> {
          this.diagram.addLink(link.withLabel(Display.create(" ")));
          this.diagram.addLink(toCommunicationLink(nonCommunicationLink, link.getGroupId()));
        });
  }

  private Runnable addNewLink(final Link nonCommunicationLink) {
    return () -> this.diagram.addLink(toCommunicationLink(nonCommunicationLink, UUID.randomUUID()));
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
