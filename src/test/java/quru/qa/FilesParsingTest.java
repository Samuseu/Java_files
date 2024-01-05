package quru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.*;


public class FilesParsingTest { // все что можно делать с pdf

    ClassLoader cl = FilesParsingTest.class.getClassLoader(); // механизм джавы который загружает все классы,это строчка говорит дай мне класс где загружен этот файл нужен для считывания

    @Test
    void pdfParseTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloadPdf = $("a[href='junit-user-guide-5.10.1.pdf']").download();
        PDF content = new PDF(downloadPdf);
        assertThat(content.author).contains("Sam Brannen"); // этот ассерт проверяем есть ли что то в файле.
    }

    @Test
    void xlsParseTest() throws Exception { // тут надо поиграть с зависимостями чтобы они не конфликтовали
        try (InputStream resourceAsStream = cl.getResourceAsStream("group_36_db_users.xlsx")) {
            XLS contetn = new XLS(resourceAsStream);
            assertThat(contetn.excel.getSheetAt(0).getRow(1).getCell(1));
        }
    }

    @Test
    void csvParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("qa_quru.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(resource))
        ) {
            List<String[]> content = reader.readAll();
           assertThat(content.get(0)[1]).contains("lesson");
        }
    }

    @Test
    void zipParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("qa_quru.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(resource))
        ) {
            List<String[]> content = reader.readAll();
            assertThat(content.get(0)[1]).contains("lesson");
}


