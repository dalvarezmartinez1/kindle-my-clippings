package net.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Clippings {

  public static final String HIGHLIGHT_SEPARATOR = "==========";
  public static final String NEWLINE_CHAR = "\n";
  public static final int ABREVIATED_ENTRY_LENGTH = 50;

  public static void main(String[] args) throws IOException {
    String content = readFile(args[0]);
    String[] highlights = content.split(HIGHLIGHT_SEPARATOR);
    Map<String, List<String>> bookToHighlights = mapHighlightsPerBook(highlights);
    for (Map.Entry<String, List<String>> mapEntry : bookToHighlights.entrySet()) {
      outputToFile(mapEntry.getKey(), mapEntry.getValue());
    }
  }

  private static String readFile(String filePath) throws IOException {
    StringBuilder contentBuilder = new StringBuilder();
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      stream.forEach(s -> contentBuilder.append(s).append(NEWLINE_CHAR));
    }
    return contentBuilder.toString();
  }

  private static Map<String, List<String>> mapHighlightsPerBook(String[] highlights) {
    Map<String, List<String>> bookToHighlights = new HashMap<>();
    for (String highlight : highlights) {
      if (!highlight.isBlank()) {
        String[] highlightParts = Arrays.stream(highlight.split(NEWLINE_CHAR)).filter(s -> !s.isBlank()).toArray(String[]::new);

        String currentEntryBookTitle = highlightParts[0];
        if (highlightParts.length != 3) {
          System.out.println(getInvalidEntryMessage(currentEntryBookTitle, bookToHighlights.get(currentEntryBookTitle)));
        } else {
          String currentEntryBookHighlight = highlightParts[2];
          if (!bookToHighlights.containsKey(currentEntryBookTitle)) {
            bookToHighlights.put(currentEntryBookTitle, new ArrayList<>(List.of(currentEntryBookHighlight)));
          } else {
            bookToHighlights.get(currentEntryBookTitle).add(currentEntryBookHighlight);
          }
        }
      }
    }
    return bookToHighlights;
  }

  private static String getInvalidEntryMessage(String bookTitle, List<String> highlights) {
    if (highlights.isEmpty()) {
      return String.format("The first entry in the book %s is not valid", bookTitle);
    } else {
      String lastEntry = highlights.get(highlights.size() - 1).substring(0, ABREVIATED_ENTRY_LENGTH) + "...";
      return String.format("Entry %d following '%s' of book %s is not valid", highlights.size(), lastEntry, bookTitle);
    }
  }

  private static void outputToFile(String key, List<String> highlights) throws FileNotFoundException {
    try (PrintWriter out = new PrintWriter(key + ".txt")) {
      for (int i = 0; i < highlights.size(); i++) {
        out.println(i + 1 + ". " + highlights.get(i) + NEWLINE_CHAR);
      }
    }
  }
}
