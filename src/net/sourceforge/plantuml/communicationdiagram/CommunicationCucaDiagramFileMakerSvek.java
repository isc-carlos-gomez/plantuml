package net.sourceforge.plantuml.communicationdiagram;

import java.io.IOException;

import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.cucadiagram.CucaDiagram;
import net.sourceforge.plantuml.cucadiagram.dot.DotData;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.style.ClockwiseTopRightBottomLeft;
import net.sourceforge.plantuml.svek.CucaDiagramFileMakerSvek;
import net.sourceforge.plantuml.svek.GeneralImageBuilder;
import net.sourceforge.plantuml.svek.TextBlockBackcolored;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;
import net.sourceforge.plantuml.ugraphic.color.HColor;

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
    return new CommunicationGeneralImageBuilder(
        this.diagram.mergeIntricated(),
        dotData,
        this.diagram.getEntityFactory(),
        this.diagram.getSource(),
        this.diagram.getPragma(),
        stringBounder,
        this.diagram.getUmlDiagramType().getStyleName());
  }

  @Override
  protected ImageBuilder newImageBuilder(final FileFormatOption fileFormatOption, final TextBlockBackcolored result,
      final String warningOrError, final double scale, final ClockwiseTopRightBottomLeft margins,
      final HColor backcolor) {
    return new CommunicationImageBuilder(
        this.diagram.getSkinParam(),
        this.diagram.getAnimation(),
        fileFormatOption.isWithMetadata() ? this.diagram.getMetadata() : null,
        warningOrError,
        scale,
        backcolor,
        margins);
  }

}
