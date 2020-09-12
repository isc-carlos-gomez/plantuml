package net.sourceforge.plantuml.communicationdiagram;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.classdiagram.ClassDiagramFactory;
import net.sourceforge.plantuml.command.Command;
import net.sourceforge.plantuml.communicationdiagram.link.CommandCommunicationLink;

/**
 * Factory of {@link CommunicationDiagram}s.
 *
 * @author Carlos Gomez
 */
public class CommunicationDiagramFactory extends ClassDiagramFactory {

  private final ISkinSimple skinParam;

  /**
   * Creates a new instance.
   *
   * @param skinParam
   *        skin (look & feel) parameters that will be used to create Communication Diagrams
   */
  public CommunicationDiagramFactory(final ISkinSimple skinParam) {
    super(skinParam);
    this.skinParam = skinParam;
  }

  @Override
  public ClassDiagram createEmptyDiagram() {
    return new CommunicationDiagram(this.skinParam);
  }

  @Override
  @SuppressWarnings("rawtypes")
  protected List<Command> createCommands() {
    final List<Command> commands = new ArrayList<>();
    commands.add(new CommandCommunicationDiagram());
    commands.add(new CommandCommunicationLink());
    commands.addAll(super.createCommands());
    return commands;
  }

}
