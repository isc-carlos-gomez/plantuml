package net.sourceforge.plantuml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.BeforeClass;
import org.junit.Test;

import net.sourceforge.plantuml.core.DiagramDescription;

public class UmlEndToEndTest {

  @BeforeClass
  public static void createOutputDir() throws IOException {
    Files.createDirectories(Paths.get(outputDir()));
  }

  @Test
  public void generateCommunicationDiagrams() throws Exception {
    assertThatDiagramsAreGenerated("/diagrams/communication");
  }

  @Test
  public void generateOtherDiagrams() throws Exception {
    assertThatDiagramsAreGenerated("/diagrams/other");
  }

  private void assertThatDiagramsAreGenerated(final String diagramsPath) throws IOException, URISyntaxException {
    final URL resource = getClass().getResource(diagramsPath);
    Files.walkFileTree(Paths.get(resource.toURI()), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
          throws IOException {
        if (Files.isRegularFile(file)) {
          assertThatDiagramIsGenerated(file);
        }
        return FileVisitResult.CONTINUE;
      }
    });
  }

  private void assertThatDiagramIsGenerated(final Path path) {
    try {
      assertNotNull(generateDiagram(path));
    } catch (final IOException e) {
      fail(e.toString());
    }
  }

  private DiagramDescription generateDiagram(final Path path) throws IOException {
    final String source = new String(Files.readAllBytes(path));
    return new SourceStringReader(source).outputImage(outputFile(path));
  }

  private OutputStream outputFile(final Path path) throws IOException {
    final String fileName = path.getFileName().toString().replace(".puml", ".png");
    final Path outputPath = Paths.get(outputDir(), fileName);
    Files.deleteIfExists(outputPath);
    return Files.newOutputStream(outputPath, StandardOpenOption.CREATE);
  }

  private static String outputDir() {
    return new File("target/test-diagrams").getAbsolutePath();
  }

}
