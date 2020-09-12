package net.sourceforge.plantuml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.Test;

import net.sourceforge.plantuml.core.DiagramDescription;

class UmlEndToEndTest {

  private static final String OUTPUT_DIR = "/Users/Carlos/Projects/PlantUML";

  @Test
  void generateCommunicationDiagrams() throws Exception {
    assertThatDiagramsAreGenerated("/diagrams/communication/communication-return.puml");
  }

  @Test
  void generateOtherDiagrams() throws Exception {
    assertThatDiagramsAreGenerated("/diagrams/other");
  }

  private void assertThatDiagramsAreGenerated(final String diagramsPath) throws IOException, URISyntaxException {
    final URL resource = getClass().getResource(diagramsPath);
    Files.walk(Paths.get(resource.toURI()))
        .filter(Files::isRegularFile)
        .forEach(this::assertThatDiagramIsGenerated);
  }

  private void assertThatDiagramIsGenerated(final Path path) {
    try {
      assertThat(generateDiagram(path)).isNotNull();
    } catch (final IOException e) {
      fail(e);
    }
  }

  private DiagramDescription generateDiagram(final Path path) throws IOException {
    final String source = new String(Files.readAllBytes(path));
    return new SourceStringReader(source).outputImage(outputFile(path));
  }

  private OutputStream outputFile(final Path path) throws IOException {
    final Path outputPath = Paths.get(OUTPUT_DIR, path.getFileName().toString().replace(".puml", ".png"));
    Files.deleteIfExists(outputPath);
    return Files.newOutputStream(outputPath, StandardOpenOption.CREATE);
  }

}
