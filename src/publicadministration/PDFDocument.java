package publicadministration;

import data.DocPath;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.Objects;

public class PDFDocument { // Represents a PDF document
    private Date creatDate;
    private DocPath path;
    private File file;


    public PDFDocument() {
        this.creatDate = new Date();
        this.path = new DocPath("src/Docs/");
        this.file = new File("default.pdf");
    } // Initializes attributes and emulates the document download at a default path

    public PDFDocument(DocPath path) {
        this.creatDate = new Date();
        this.path = path;
        this.file = new File("default.pdf");
    }

    public PDFDocument(File file) {
        this.creatDate = new Date();
        this.path = new DocPath("src/Docs/");;
        this.file = file;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public DocPath getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "PDFDocument{" +
                "creatDate=" + creatDate +
                ", path=" + path +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFDocument that = (PDFDocument) o;
        return Objects.equals(creatDate, that.creatDate) && Objects.equals(path, that.path) && Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creatDate, path, file);
    }

    // To implement only optionally
    public void moveDoc(DocPath destPath) throws Exception // Moves the document to the destination path indicated
    {

        Path origenPath = FileSystems.getDefault().getPath(path.getPath()+getFile().getPath());
        Path destinoPath = FileSystems.getDefault().getPath(destPath.getPath());

        try {
            Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException();
        }

    }

    public void openDoc(DocPath path) throws Exception // Opens the document at the path indicated
    {
        try {
            Desktop.getDesktop().open(new File(path.getPath()+getFile()));
        } catch (Exception ex) {
            throw new Exception();
        }
    }

}

