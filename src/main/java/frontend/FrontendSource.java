package frontend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FrontendSource {

    public String get() {
        try{
            String fullName = new File("").getAbsolutePath() + "/src/main/java/frontend/" + "ejemplo" + ".html";
            BufferedReader br = new BufferedReader(new FileReader(fullName));
            String line = null;
            String page = "";
            while ((line = br.readLine()) != null) {
                page = page + line;

            }
            return page;
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

    }

}
