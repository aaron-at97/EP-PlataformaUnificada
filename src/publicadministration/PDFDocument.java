package publicadministration;

import data.DocPath;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class PDFDocument { // Represents a PDF document
    private Date creatDate;
    private DocPath path;
    private File file;

    public PDFDocument () {  } // Initializes attributes and emulates the document download at a default path
    //??? // the getters

    //public String toString () {  } // Converts to String members Date and DocPath

    // To implement only optionally
    public void moveDoc(DocPath destPath) throws IOException // Moves the document to the destination path indicated
    {

    }

    public void openDoc(DocPath path) throws IOException // Opens the document at the path indicated
    {

    }

}

