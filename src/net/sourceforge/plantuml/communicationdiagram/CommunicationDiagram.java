package net.sourceforge.plantuml.communicationdiagram;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;

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

}
