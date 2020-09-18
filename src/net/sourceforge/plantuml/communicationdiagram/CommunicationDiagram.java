package net.sourceforge.plantuml.communicationdiagram;

import java.io.IOException;

import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.communicationdiagram.link.CommunicationLink;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.svek.CucaDiagramFileMaker;

/**
 * A <a href="https://www.uml-diagrams.org/communication-diagrams.html">UML Communication
 * Diagram</a>.
 * <p>
 * For PlantUML a Communication Diagram is a Class Diagram where the links between entities are
 * grouped to be rendered as single link with arrow messages floating around.
 *
 * @author Carlos Gomez
 */
public class CommunicationDiagram extends ClassDiagram {

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
   * Removes the last link from this diagram.
   *
   * @return the removed link
   */
  public Link removeLastLink() {
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
  public void replaceLink(final Link existingLink, final Link newLink) {
    removeLink(existingLink);
    addLink(newLink);
  }

  /**
   * Finds the first link that has the same ends (aka. entities) as the given link in this diagram.
   *
   * @param link
   *        the link to compare
   * @return the found link if any, null otherwise
   */
  public CommunicationLink findLinkWithSameEnds(final Link link) {
    for (final Link existingLink : getLinks()) {
      final CommunicationLink communicationLink = (CommunicationLink) existingLink;
      if (communicationLink.hasSameEnds(link)) {
        return communicationLink;
      }
    }
    return null;
  }

  /**
   * Finds the first link that has ends (aka. entities) opposite to the given link in this diagram.
   *
   * @param link
   *        the link to compare
   * @return the found link if any, null otherwise
   */
  public CommunicationLink findLinkWithOppositeEnds(final Link link) {
    for (final Link existingLink : getLinks()) {
      final CommunicationLink communicationLink = (CommunicationLink) existingLink;
      if (communicationLink.hasOppositeEnds(link)) {
        return communicationLink;
      }
    }
    return null;
  }

  @Override
  protected CucaDiagramFileMaker newCucaDiagramFileMaker(final FileFormatOption fileFormatOption) throws IOException {
    if (isUseJDot()) {
      throw new IllegalStateException("JDot not supported for communication diagrams");
    }
    return new CommunicationCucaDiagramFileMakerSvek(this);
  }

}
