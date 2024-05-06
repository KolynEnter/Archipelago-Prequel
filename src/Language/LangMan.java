package Language;

import java.util.ArrayList;

public class LangMan {

    public static final ArrayList<ArrayList<String>> matrix = new ArrayList<>();

    public LangMan() {

    }

    public void loadAllLanguagePacks() {
        matrix.clear();
        for (LangFile type : LangFile.values()) {
            ArrayList<String> fileContent = Lang.readFileContents("/" + type + ".txt");
            matrix.add(fileContent);
        }
    }

    public static String getContent(LangFile type, int position) {
        return LangMan.matrix.get(type.ordinal()).get(position);
    }
}
