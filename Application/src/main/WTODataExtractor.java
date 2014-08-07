package main;

import java.io.*;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author Denys Lazarenko
 */
public class WTODataExtractor {
	
	public static void main(String[] args) { 
		analyzeDocument();
	}
	
	public static void analyzeDocument() {
		long startTime = System.currentTimeMillis();
		String fileName = "files\\M264A1.docx";

		// Opening a document
		try {
			InputStream is = new FileInputStream(fileName); 
		    XWPFDocument doc = new XWPFDocument(is);
		
		    XWPFDocument newDoc = copyDocument(doc);
		
		    // Writing a new XWPFDocument to the disk after processing the input file. 
		    FileOutputStream os = new FileOutputStream(new File("files\\M264A1_output.docx"));
	        
		    // Writing a file and closing the  output stream. 
		    // Later, every resulting output file will have its own FileOutputStream.
		    newDoc.write(os);
	        os.close();		
	        
	        // It's always a good idea to close input stream after using it. 
	        is.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.println("executed in " + (System.currentTimeMillis() - startTime) + " ms");
	}
	

    /**
	 * Reads an XWPFDocument object, creates a new XWPFDocument and fills it with some information from an input document.
	 * 
	 * TODO: for a moment, this method just copies all the paragraphs in new document.
	 * But later, we will extract country information and this method will return not a single XWPFDocument object,
	 * but a List<XWPFDocument>, and then in main function those documents will be written to the disk, using FileOutputStream.
	 * 
	 * TODO: one more thing to fix is numeration. For a moment, new document doesn't contain ANY numeration, it is completely lost.
	 * The idea is to use oldDoc.getNumbering() and to work with XWPFNumbering object.
	 */
	public static XWPFDocument copyDocument(XWPFDocument oldDoc) {	

		// Creating one new document.
		// In future we will create AS MANY documents, AS COUNTRIES mentioned in text.
	    XWPFDocument newDoc = new XWPFDocument();  
		     		      		      
	    // Extracting all paragraphs.
	    List<XWPFParagraph> paragraphs = oldDoc.getParagraphs();
	    for (XWPFParagraph para : paragraphs) {  
	    	if (!para.getParagraphText().isEmpty()) {       
	    		XWPFParagraph newpara = newDoc.createParagraph();
	            copyAllRunsToAnotherParagraph(para, newpara);
		    }	    	
		}	
		    
	    // Just for information
		System.out.println("\nTotal paragraphs in document: " + paragraphs.size());
		    
		return newDoc;
	} 
	
	/**
	 *  Copy all runs from one paragraph to another, keeping the font unchanged.
	 *  
	 *  TODO: fontSize is -1 for the majority of XWPFRun objects. 
	 *  But do we really care about initial font size? We can always set the size we want for the output file. 
	 */
	private static void copyAllRunsToAnotherParagraph(XWPFParagraph oldPar, XWPFParagraph newPar) {
	    final int DEFAULT_FONT_SIZE = 10;

	   for (XWPFRun run : oldPar.getRuns()) {
	   
	        String textInRun = run.getText(0);
	        
	        // We pass to the next run, if a given run is empty.
	        if (textInRun == null || textInRun.isEmpty()) {
	            continue;
	        }

	        int fontSize = run.getFontSize();
	        //System.out.println("run text = '" + textInRun + "' , fontSize = " + fontSize); 

	        XWPFRun newRun = newPar.createRun();

	        // Copying text
	        newRun.setText(textInRun);

	        // Applying the same style as in the input run
	        newRun.setFontSize( ( fontSize == -1) ? DEFAULT_FONT_SIZE : run.getFontSize() );    
	        newRun.setFontFamily( run.getFontFamily() );
	        newRun.setBold( run.isBold() );
	        newRun.setItalic( run.isItalic() );
	        newRun.setStrike( run.isStrike() );
	        newRun.setColor( run.getColor() );
	        newRun.setUnderline(run.getUnderline());
	    }   
	}
	
} // end class
