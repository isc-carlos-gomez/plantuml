package net.sourceforge.plantuml.communicationdiagram;

import java.io.IOException;

import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.cucadiagram.CucaDiagram;
import net.sourceforge.plantuml.cucadiagram.dot.DotData;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.svek.CucaDiagramFileMakerSvek;
import net.sourceforge.plantuml.svek.GeneralImageBuilder;
import net.sourceforge.plantuml.svek.TextBlockBackcolored;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;

/**
 * Customize {@link CucaDiagramFileMakerSvek} to support Communication Diagrams by creating custom
 * image builders.
 *
 * @author Carlos Gomez
 */
class CommunicationCucaDiagramFileMakerSvek extends CucaDiagramFileMakerSvek {

  private final CucaDiagram diagram;

  CommunicationCucaDiagramFileMakerSvek(final CommunicationDiagram diagram) throws IOException {
    super(diagram);
    this.diagram = diagram;
  }

  @Override
  protected GeneralImageBuilder newGeneralImageBuilder(final StringBounder stringBounder, final DotData dotData) {
    return new CommunicationGeneralImageBuilder(dotData, this.diagram.getEntityFactory(), this.diagram.getSource(),
        this.diagram.getPragma(), stringBounder);
  }

  @Override
  protected ImageBuilder newImageBuilder(final FileFormatOption fileFormatOption, final TextBlockBackcolored result,
      final String warningOrError, final double scale) {
    return new CommunicationImageBuilder(this.diagram.getSkinParam(), scale,
        fileFormatOption.isWithMetadata() ? this.diagram.getMetadata() : null, warningOrError, 0, 10,
        this.diagram.getAnimation(), result.getBackcolor());
  }

}
