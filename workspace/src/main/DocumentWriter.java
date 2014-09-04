package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

public class DocumentWriter {
  private static final String FILES_FOLDER = "files";

  // Saves the document on disk in .docx format.
  public static void saveDocx(String filePath, XWPFDocument document) {

    FileOutputStream out = null;
    try {
      out = new FileOutputStream(filePath);
      document.write(out);
    } catch (Exception e) {
      System.out.println("\t\t!!! Can't write to the file " + filePath);
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (IOException e) {
        System.out.println("Error of reading/writing of the file " + filePath);
        System.out.println("Error message: " + e.getMessage());
      }
    }
    System.out.println("\tFile '" + filePath + "' saved!\n");
  }

  /**
   * Reads an XWPFDocument object, creates a new XWPFDocument and fills it with some information
   * from an input document. TODO: for a moment, this method just copies all the paragraphs in new
   * document. But later, we will extract country information and this method will return not a
   * single XWPFDocument object, but a List<XWPFDocument>, and then in main function those documents
   * will be written to the disk, using FileOutputStream. TODO: one more thing to fix is numeration.
   * For a moment, new document doesn't contain ANY numeration, it is completely lost. The idea is
   * to use oldDoc.getNumbering() and to work with XWPFNumbering object.
   */
  public static void copyDocument(String fileName) {

    try {
      // Opening a document
      InputStream input = new FileInputStream(FILES_FOLDER + File.separator + fileName);
      XWPFDocument oldDoc = new XWPFDocument(input);

      // Creating one new document.
      // In future we will create AS MANY documents, AS COUNTRIES mentioned in text.
      XWPFDocument newDoc = new XWPFDocument();

      // Extracting all paragraphs.
      List<XWPFParagraph> paragraphs = oldDoc.getParagraphs();
      for (XWPFParagraph oldPar : paragraphs) {
        if (!oldPar.getParagraphText().isEmpty()) {
          XWPFParagraph newPar = newDoc.createParagraph();
          cloneParagraph(newPar, oldPar);
        }
      }

      // Just for information
      System.out.println("\nTotal paragraphs in document: " + paragraphs.size());


      // Writing a new XWPFDocument to the disk after processing the input file.
      String outputFilename = fileName.substring(0, fileName.indexOf(".docx")) + "_output.docx";
      DocumentWriter.saveDocx(FILES_FOLDER + File.separator + outputFilename, newDoc);

      // It's always a good idea to close input stream after using it.
      input.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  private static void cloneParagraph(XWPFParagraph clone, XWPFParagraph source) {
    CTPPr pPr = clone.getCTP().isSetPPr() ? clone.getCTP().getPPr() : clone.getCTP().addNewPPr();
    pPr.set(source.getCTP().getPPr());
    for (XWPFRun run : source.getRuns()) {
      XWPFRun newRun = clone.createRun();
      cloneRun(newRun, run);
    }
  }

  private static void cloneRun(XWPFRun clone, XWPFRun source) {
    CTRPr rPr = clone.getCTR().isSetRPr() ? clone.getCTR().getRPr() : clone.getCTR().addNewRPr();
    rPr.set(source.getCTR().getRPr());
    clone.setText(source.getText(0));
  }

  // TODO replace by "cloneParagraph"
  /**
   * Copy all runs from one paragraph to another, keeping the font unchanged. TODO: fontSize is -1
   * for the majority of XWPFRun objects. But do we really care about initial font size? We can
   * always set the size we want for the output file.
   */
  // private static void copyAllRunsToAnotherParagraph(XWPFParagraph oldPar, XWPFParagraph newPar) {
  // final int DEFAULT_FONT_SIZE = 10;
  //
  // for (XWPFRun run : oldPar.getRuns()) {
  //
  // String textInRun = run.getText(0);
  //
  // // We pass to the next run, if a given run is empty.
  // if (textInRun == null || textInRun.isEmpty()) {
  // continue;
  // }
  //
  // int fontSize = run.getFontSize();
  // // System.out.println("run text = '" + textInRun + "' , fontSize = " + fontSize);
  //
  // XWPFRun newRun = newPar.createRun();
  //
  // // Copying text
  // newRun.setText(textInRun);
  //
  // // Applying the same style as in the input run
  // newRun.setFontSize((fontSize == -1) ? DEFAULT_FONT_SIZE : run.getFontSize());
  // newRun.setFontFamily(run.getFontFamily());
  // newRun.setBold(run.isBold());
  // newRun.setItalic(run.isItalic());
  // newRun.setStrike(run.isStrike());
  // newRun.setColor(run.getColor());
  // newRun.setUnderline(run.getUnderline());
  // }
  // }

}
