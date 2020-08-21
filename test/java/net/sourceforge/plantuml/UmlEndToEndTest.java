package net.sourceforge.plantuml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import net.sourceforge.plantuml.core.DiagramDescription;

class UmlEndToEndTest {

  private static final String OUTPUT_DIR = "/Users/Carlos/Projects/PlantUML";

  @Test
  @Disabled("Won't work because classes diagrams are changed to support communication diagrams")
  void generateClassDiagram() throws IOException {
    String source = "@startuml\n";
    source += "Class01 <|-- Class02\n";
    source += "@enduml\n";

    final Path path = Paths.get("/Users/Carlos/Projects/PlantUML/ClassDiagram.png");
    Files.deleteIfExists(path);
    final OutputStream png = Files.newOutputStream(path, StandardOpenOption.CREATE);

    final SourceStringReader reader = new SourceStringReader(source);
    // Write the first image to "png"
    final DiagramDescription outputImage = reader.outputImage(png);
    final String desc = outputImage.getDescription();
    // Return a null string if no generation

    assertThat(desc).isEqualTo("test");
  }

  @Test
  void generateCollaborationDiagrams() throws Exception {
    final URL resource = getClass().getResource("/diagrams/collaboration");
    Files.walk(Paths.get(resource.toURI()))
        .filter(Files::isRegularFile)
        .forEach(path -> {
          try {
            assertThat(generateDiagram(path)).isNotNull();
          } catch (final IOException e) {
            fail(e);
          }
        });
  }

  @Test
  void generateOtherDiagrams() throws Exception {
    final URL resource = getClass().getResource("/diagrams/other");
    Files.walk(Paths.get(resource.toURI()))
        .filter(Files::isRegularFile)
        .forEach(path -> {
          try {
            assertThat(generateDiagram(path)).isNotNull();
          } catch (final IOException e) {
            fail(e);
          }
        });
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
