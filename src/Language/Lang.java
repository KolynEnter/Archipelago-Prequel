package Language;

import Backend.FileManagers.MyInputManager;
import Backend.FileManagers.MyOutputManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Objects;

public class Lang {

    public static String currentLanguage = Lang.readCurrentLanguage();

    public static String readCurrentLanguage() {
        MyInputManager inputManager = new MyInputManager("lib/Language/CurrentLanguage.txt");
        String result = inputManager.readTheOnlyLineInFile();
        inputManager.closeBoth();

        return result;
    }

    public static void rewriteCurrentLanguage(String newLanguage) {
        MyOutputManager outputManager = new MyOutputManager("lib/Language/CurrentLanguage.txt");
        outputManager.writeOneLine(newLanguage);
        outputManager.closeWriter();
    }

    public static ArrayList<String> readFileContents(String specifiedPath) {
        ArrayList<String> storage = new ArrayList<>();
        MyInputManager inputManager = new MyInputManager("lib/Language/Pack/" + Lang.currentLanguage + specifiedPath);
        storage = inputManager.readFileAsStringArrayList();
        inputManager.closeBoth();
        return storage;
    }

    public static String adaptLineToMessage(Object[] variables, String line) {
        for (int i = 0; i < variables.length; i++) {
            String replacer = "@" + i;
            if (variables[i] instanceof Float) {
                line = line.replaceAll(replacer, new DecimalFormat("0.0").format((float) variables[i]));
            } else {
                line = line.replaceAll(replacer, variables[i].toString());
            }
        }

        return line;
    }
}
