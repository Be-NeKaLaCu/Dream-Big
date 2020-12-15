package Recruit;

import Recruit.Company.SNURecruit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MockRecruit extends SNURecruit {
    @Override
    protected String getRecruitPage(int page) {
        try {
            return readFileInResources("MockRecruit/page_" + page);
        } catch (IOException e) {
            return "";
        }
    }

    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

}
