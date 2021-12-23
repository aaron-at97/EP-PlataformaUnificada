package publicadministration;

import data.DocPath;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class PDFDocument { // Represents a PDF document
    private Date creatDate;
    private DocPath path;
    private File file;

    public PDFDocument () {
        this.creatDate = new Date();
        this.path = new DocPath("/../../Docs/default.pdf");
        try {
            this.file = new File(path.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // Initializes attributes and emulates the document download at a default path

    public Date getCreatDate() {
        return creatDate;
    }

    public DocPath getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "PDFDocument{" +
                "creatDate=" + creatDate +
                ", path=" + path +
                '}';
    }

    // To implement only optionally
    public void moveDoc(DocPath destPath) throws IOException // Moves the document to the destination path indicated
    {

    }

    public void openDoc(DocPath path) throws IOException // Opens the document at the path indicated
    {
        try {
            Desktop.getDesktop().open(new File(path.getPath()));
        } catch (IOException ex) {
            throw new IOException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

