package Backend.FileManagers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyOutputManager {

    private BufferedWriter bw;

    public MyOutputManager(String filePath) {
        assignWriter(filePath);
    }

    private void assignWriter(String filePath) {
        File file = new File(filePath);
        bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOneLine(String content) {
        try {
            assert bw != null;
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter() {
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
