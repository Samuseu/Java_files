package quru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideFilesTests {
// это если нету ссылки href только использовать если нету хреф а то тесты будут не стабильны
    static {
        Configuration.fileDownload = FileDownloadMode.PROXY;
    }

    // это если есть ссылка href
    @Test
    void selenideDownloadTest() throws Exception {
        open("https://github.com/Samuseu/JUnit5/blob/main/README.md");
        File downloadedFile = $("[data-testid='raw-button']").download();
        try (InputStream is = new FileInputStream(downloadedFile)) { // в скобках то что закроет это вместо is.close();
            byte[] bytes = is.readAllBytes();
            String textContent = new String(bytes, StandardCharsets.UTF_8);
            assertThat(textContent).contains("This repository is the home of _JUnit 5_.");
        }
    }

    @Test
    void selenideUploadFile(){
        open("https://fineuploader.com/demos.html");
        $("input[type='file']").uploadFile(new File("build/downloads/b0536fda-a62a-4bb7-9b4c-6e47702e115e/README.md"));
    }
}
