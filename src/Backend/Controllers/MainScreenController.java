package Backend.Controllers;

import Backend.GameCore;
import Frontend.Screens.MainScreen;
import Frontend.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenController {

    private final UI ui;
    private final MainScreenHandler mainScreenHandler = new MainScreenHandler();
    private final LangController langController = new LangController(this);

    public MainScreenController(UI ui) {
        this.ui = ui;
    }

    public class MainScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "1":
                    MainScreen mainScreen = ui.getMainScreen();
                    mainScreen.assignChangeLangPanel(langController.getLangHandler());
                    System.out.println("Language");
                    break;
                case "2":
                    System.out.println("Audio Setting");
                    break;
                case "3":
                    System.out.println("Save And Exit");
                    break;
                default:
                    System.out.println("MAIN SCREEN CONTROLLER ERROR.");
                    break;
            }
        }
    }

    public MainScreenHandler getMainScreenHandler() {
        return mainScreenHandler;
    }

    public MainScreen getMainScreen() {
        return ui.getMainScreen();
    }
}
