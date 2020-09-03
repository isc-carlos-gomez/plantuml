package net.sourceforge.plantuml.communicationdiagram;

import java.util.Optional;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.cucadiagram.Link;

/**
 * A <a href="https://www.uml-diagrams.org/communication-diagrams.html">UML Communication
 * Diagram</a>.
 * <p>
 * For PlantUML a Communication Diagram is a Class Diagram where the links between entities are
 * grouped to be rendered as single link with arrow messages floating around.
 *
 * @author Carlos Gomez
 */
class CommunicationDiagram extends ClassDiagram {

  /**
   * Creates a new instance.
   *
   * @param skinParam
   *        skin (look & feel) parameters that will be used to create this diagram
   */
  CommunicationDiagram(final ISkinSimple skinParam) {
    super(skinParam);
  }

  /**
   * Ensures that the last link of this diagram is a communication link.
   *
   * @param linkProperties
   *        properties that will be used to transform the last link into a communication link
   */
  void ensureLastLinkIsCommunicationLink(final CommunicationLinkProperties linkProperties) {
    new LastLinkToCommunicationLinkTransformer(this, linkProperties)
        .transform();
  }

  /**
   * Removes the last link from this diagram.
   *
   * @return the removed link
   */
  Link removeLastLink() {
    final Link lastLink = getLastLink();
    removeLink(lastLink);
    return lastLink;
  }

  /**
   * Replaces two links in this diagram.
   *
   * @param existingLink
   *        the link that will be replaced
   * @param newLink
   *        the replacing link
   */
  void replaceLink(final Link existingLink, final Link newLink) {
    removeLink(existingLink);
    addLink(newLink);
  }

  /**
   * Finds the first link that has the same ends (aka. entities) as the given link in this diagram.
   *
   * @param link
   *        the link to compare
   * @return the found link, if any
   */
  Optional<CommunicationLink> findLinkWithSameEnds(final Link link) {
    return getLinks().stream()
        .map(CommunicationLink.class::cast)
        .filter(communicationLink -> communicationLink.hasSameEnds(link))
        .findFirst();
  }

  /**
   * Finds the first link that has ends (aka. entities) opposite to the given link in this diagram.
   *
   * @param link
   *        the link to compare
   * @return the found link, if any
   */
  Optional<CommunicationLink> findLinkWithOppositeEnds(final Link link) {
    return getLinks().stream()
        .map(CommunicationLink.class::cast)
        .filter(communicationLink -> communicationLink.hasOppositeEnds(link))
        .findFirst();
  }

}
