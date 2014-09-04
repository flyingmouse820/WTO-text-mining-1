package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DocumentParser {
  private static final String FILES_FOLDER = "files";

  public List<String> extractAllCountryNames(String fileName) {
    List<String> countryNames = new ArrayList<String>();


    try {
      // Opening a document
      InputStream input = new FileInputStream(FILES_FOLDER + File.separator + fileName);
      XWPFDocument document = new XWPFDocument(input);
      // Extracting all paragraphs.
      List<XWPFParagraph> paragraphs = document.getParagraphs();
      for (XWPFParagraph paragraph : paragraphs) {
        if (!paragraph.getParagraphText().isEmpty()) {

          if (getBoldUnderlinedText(paragraph) != "") {
            System.out.println("bold underlined text = " + getBoldUnderlinedText(paragraph));
          }
        }
      }


      // It's always a good idea to close input stream after using it.
      input.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return countryNames;
  }

  private static String getUnderlinedText(XWPFParagraph paragraph) {
    String underlinedText = "";

    for (XWPFRun run : paragraph.getRuns()) {

      // See https://poi.apache.org/apidocs/org/apache/poi/xwpf/usermodel/UnderlinePatterns.html
      if (run.getUnderline() != UnderlinePatterns.NONE) {
        underlinedText += run.getText(0) + " ";
      }
    }

    return underlinedText;
  }

  private static String getBoldText(XWPFParagraph paragraph) {
    String boldText = "";

    for (XWPFRun run : paragraph.getRuns()) {
      if (run.isBold()) {
        boldText += run.getText(0) + " ";
      }
    }

    return boldText;
  }

  private static String getBoldUnderlinedText(XWPFParagraph paragraph) {
    String boldUnderlinedText = "";

    for (XWPFRun run : paragraph.getRuns()) {

      // See https://poi.apache.org/apidocs/org/apache/poi/xwpf/usermodel/UnderlinePatterns.html
      if (run.getUnderline() != UnderlinePatterns.NONE && run.isBold()) {
        boldUnderlinedText += run.getText(0) + " ";
      }
    }

    return boldUnderlinedText;
  }


  /**
   * Returns a statistic of all styleIDs in the document.
   * 
   * @param fileName name of document to analyze
   * @return a map containing all styleIDs found in the document, and the count of paragraphs which
   *         have given styleID
   */
  public Map<String, Integer> extractStyleIDs(String fileName) {

    Map<String, Integer> mapStyleID = new TreeMap<String, Integer>();

    try {
      InputStream input = new FileInputStream(FILES_FOLDER + File.separator + fileName);
      XWPFDocument doc = new XWPFDocument(input);

      List<XWPFParagraph> paragraphs = doc.getParagraphs();

      for (XWPFParagraph par : paragraphs) {
        String styleID = par.getStyleID();

        if (styleID != null) {
          if (!mapStyleID.containsKey(styleID)) {
            // New styleID found. We add an entry to the map
            mapStyleID.put(styleID, 0);
          } else {
            // The styleID is already in the map. Incrementing counter.
            mapStyleID.put(styleID, mapStyleID.get(styleID) + 1);
          }
        } else {
          // The absence of styleID we represent as a styleID with value "null"
          if (!mapStyleID.containsKey("null")) {
            mapStyleID.put("null", 0);
          } else {
            mapStyleID.put("null", mapStyleID.get("null") + 1);
          }
        }
      }
      input.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return mapStyleID;
  }

  /**
   * Returns all the paragraphs of the document which have the given styleID.
   * 
   * @param fileName name of document to analyze
   * @param targetStyleID the styleID for which we search the paragraphs
   */
  public List<XWPFParagraph> getParagraphsOfGivenStyleID(String fileName, String targetStyleID) {
    List<XWPFParagraph> paragraphs = new ArrayList<XWPFParagraph>();

    try {

      InputStream input = new FileInputStream(FILES_FOLDER + File.separator + fileName);
      XWPFDocument doc = new XWPFDocument(input);

      List<XWPFParagraph> allDocParagraphs = doc.getParagraphs();
      for (XWPFParagraph par : allDocParagraphs) {

        String styleID = par.getStyleID();
        if (styleID != null) {
          if (styleID.equals(targetStyleID)) {
            paragraphs.add(par);
          }
        } else {
          // if a paragraph doesn't contain a styleID, we add it in the final list ONLY if
          // targetStyleID is a string with value "null"
          if ("null".equals(targetStyleID)) {
            paragraphs.add(par);
          }
        }
      }

      input.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return paragraphs;
  }

}
