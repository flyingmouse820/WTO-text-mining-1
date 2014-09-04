package main;

import java.util.List;
import java.util.Map;


/**
 * @author Denys Lazarenko
 */
public class WTODataExtractor {

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();

    String file1 = "M264A1.docx";
    //
    // DocumentParser parser = new DocumentParser();
    // Map<String, Integer> styleIDs = parser.extractStyleIDs(file1);
    // System.out.println("\n\n\tStyleIDs of document " + file1 + ":\n");
    // displayMap(styleIDs);

    DocumentParser parser = new DocumentParser();
    List<String> countryNames = parser.extractAllCountryNames(file1);
    System.out.println("country names: " + countryNames);

    // DocumentWriter.copyDocument(file1); !! TODO !! causes
    // "java.lang.OutOfMemoryError: Java heap space"

    System.out.println("\n\nexecuted in " + (System.currentTimeMillis() - startTime) + " ms");
  }

  private static void displayMap(Map<String, Integer> map) {
    for (String styleID : map.keySet()) {
      System.out.println(styleID + " : \t" + map.get(styleID) + " paragraphs");
    }
  }

} // end class
