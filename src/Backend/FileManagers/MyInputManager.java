package Backend.FileManagers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MyInputManager {

    private InputStream inputStream;
    private BufferedReader reader;

    public MyInputManager(String filePath) {
        assignInputStream(filePath);
        assignReader();
    }

    private void assignInputStream(String filePath) {
        inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void assignReader() {
        assert inputStream != null;
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public String readTheOnlyLineInFile() {
        String result = null;
        try {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String[] readFileAsStringArray(int maxLen) {
        String[] result = new String[maxLen];
        int count = 0;
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (line != null) {
            result[count] = line;
        }
        count++;
        while (line != null) {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            result[count] = line;
            count++;
        }

        return result;
    }

    public ArrayList<String> readFileAsStringArrayList() {
        ArrayList<String> result = new ArrayList<>();
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line != null) {
            result.add(line);
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public HashMap<String, String> readFileAsStringStringHashMap(String regex) {
        HashMap<String, String> result = new HashMap<>();
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line != null) {
            String[] tuple = line.split(regex);
            if (tuple.length == 2) {
                result.put(tuple[0], tuple[1]);
            }
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public boolean[] readFileAsBooleanArray(int maxLen) {
        boolean[] result = new boolean[maxLen];
        int count = 0;
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (line != null) {
            result[count] = line.length() > 0;
        }
        count++;
        while (line != null) {
            try {
                line = reader.readLine();
                if (line != null) {
                    result[count] = line.length() > 0;
                } else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;
            if (count > maxLen) {
                break;
            }
        }

        return result;
    }

    public void closeBoth() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
