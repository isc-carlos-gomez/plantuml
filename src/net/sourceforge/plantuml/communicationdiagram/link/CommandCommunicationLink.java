package net.sourceforge.plantuml.communicationdiagram.link;

import net.sourceforge.plantuml.LineLocation;
import net.sourceforge.plantuml.UmlDiagramType;
import net.sourceforge.plantuml.classdiagram.command.CommandLinkClass;
import net.sourceforge.plantuml.command.BlocLines;
import net.sourceforge.plantuml.command.Command;
import net.sourceforge.plantuml.command.CommandControl;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.regex.RegexResult;
import net.sourceforge.plantuml.communicationdiagram.CommunicationDiagram;
import net.sourceforge.plantuml.objectdiagram.AbstractClassOrObjectDiagram;

/**
 * Service to execute PlantUML commands that create links in a Communication Diagram.
 *
 * @author Carlos Gomez
 */
public class CommandCommunicationLink implements Command<CommunicationDiagram> {

  private final CommunicationCommandLinkClass delegate;
  private String commandString;

  public CommandCommunicationLink() {
    this.delegate = new CommunicationCommandLinkClass();
    this.commandString = "";
  }

  @Override
  public CommandExecutionResult execute(final CommunicationDiagram diagram, final BlocLines lines) {
    initCommandString(lines);
    return this.delegate.execute(diagram, lines);
  }

  @Override
  public CommandControl isValid(final BlocLines lines) {
    return this.delegate.isValid(lines);
  }

  @Override
  public String[] getDescription() {
    return this.delegate.getDescription();
  }

  private void initCommandString(final BlocLines lines) {
    if (lines.size() != 1) {
      throw new IllegalArgumentException("Unsupported command: " + lines);
    }
    this.commandString = lines.getFirst499().getString();
  }

  private class CommunicationCommandLinkClass extends CommandLinkClass {

    private final CommandArgumentSequenceDecorator sequenceDecorator;

    private CommunicationCommandLinkClass() {
      super(UmlDiagramType.CLASS);
      this.sequenceDecorator = new CommandArgumentSequenceDecorator();
    }

    @Override
    protected CommandExecutionResult executeArg(final AbstractClassOrObjectDiagram abstractDiagram,
        final LineLocation location, final RegexResult arg) {
      final CommunicationDiagram diagram = (CommunicationDiagram) abstractDiagram;
      final CommandExecutionResult result = super.executeArg(diagram, location, decorateArgument(arg));
      if (result.isOk()) {
        new LastLinkToCommunicationLinkTransformer(diagram, new CommunicationLinkProperties(arg, getDirection(arg)))
            .transform();
      }
      return result;
    }

    private RegexResult decorateArgument(final RegexResult arg) {
      return this.sequenceDecorator.decorate(arg, CommandCommunicationLink.this.commandString);
    }
  }
}
