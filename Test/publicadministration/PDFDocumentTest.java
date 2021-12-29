package publicadministration;

import data.DocPath;
import data.Nif;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.NotAffiliatedException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PDFDocumentTest {
    PDFDocument pdf;
    static SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
    @BeforeEach
    void init(){
        pdf = new PDFDocument();
    }

    @Test
    void open() throws Exception {
        pdf.openDoc(new DocPath("src/Docs/"));
    }

    @Test
    void move() throws Exception {

        pdf.moveDoc(new DocPath("src/Docs/move/default.pdf"));
        PDFDocument pdf2 = new PDFDocument(new DocPath("src/Docs/move/"));
        pdf2.moveDoc(new DocPath("src/Docs/default.pdf"));
    }

    @Test
    void getCreatDate() {
        Date data = new Date();
        assertEquals(sdformat.format(data), sdformat.format(pdf.getCreatDate()));
        assertNotEquals(new Date(2020-1900, Calendar.JULY, 5), pdf.getCreatDate());
    }

    @Test
    void getPath() {
        assertEquals(new DocPath("src/Docs/"), pdf.getPath());
        assertNotEquals(new DocPath("src/arfsdf/ddsfsd/"), pdf.getPath());
    }

    @Test
    void getFile() {
        assertEquals(new File("default.pdf"), pdf.getFile());
        assertNotEquals(new File("error.pdf"), pdf.getFile());
    }
}
