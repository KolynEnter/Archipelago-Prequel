package Backend.Controllers;

import Language.Chinese;
import Language.English;
import Language.Lang;
import Language.LangMan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LangController {

    private final LangHandler langHandler = new LangHandler();
    private final MainScreenController mainScreenController;

    public LangController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public class LangHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "EN":
                    Lang lang = new Lang();
                    English english = new English();
                    new English.Chang2English().currentIsEnglish();
                    Lang.rewriteCurrentLanguage("EN");
                    new LangMan().loadAllLanguagePacks();
                    System.out.println("Change to English");
                    break;
                case "CN":
                    lang = new Lang();
                    Chinese chinese = new Chinese();
                    new Chinese.Chang2Chinese().currentIsChinese();
                    Lang.rewriteCurrentLanguage("CN");
                    new LangMan().loadAllLanguagePacks();
                    System.out.println("Change to Chinese");
                    break;
                default:
                    System.out.println("LANG CONTROLLER ERROR.");
                    break;
            }
            mainScreenController.getMainScreen().exitFromLanguageSelection();
        }
    }

    public LangHandler getLangHandler() {
        return langHandler;
    }
}
