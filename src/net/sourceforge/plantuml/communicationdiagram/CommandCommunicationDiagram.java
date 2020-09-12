package net.sourceforge.plantuml.communicationdiagram;

import net.sourceforge.plantuml.LineLocation;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.command.regex.IRegex;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexResult;

/**
 * Command to enable Communication Diagram features and allow mixing by default.
 *
 * @author Carlos Gomez
 */
class CommandCommunicationDiagram extends SingleLineCommand2<CommunicationDiagram> {

  CommandCommunicationDiagram() {
    super(pattern());
  }

  @Override
  protected CommandExecutionResult executeArg(final CommunicationDiagram diagram, final LineLocation location,
      final RegexResult arg) {
    diagram.setAllowMixing(true);
    return CommandExecutionResult.ok();
  }

  private static IRegex pattern() {
    return RegexConcat.build(
        CommandCommunicationDiagram.class.getName(),
        RegexLeaf.start(),
        new RegexLeaf("communication diagram"),
        RegexLeaf.end());
  }
}
