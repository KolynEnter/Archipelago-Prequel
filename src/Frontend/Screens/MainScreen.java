package Frontend.Screens;

import Backend.Controllers.LangController;
import Backend.Controllers.MainScreenController;
import Backend.Controllers.SwitchScreenController;
import Backend.FileManagers.MyInputManager;
import Frontend.UI;
import Frontend.UIObject;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainScreen extends UIObject{

    private final UI ui;
    private JPanel titlePanel;
    private JPanel bottomPanel;
    private final int titlePanelHeight;
    private final int bottomPanelHeight;

    public MainScreen(UI ui) {
        this.ui = ui;
        titlePanelHeight = ui.getWindowHeight()/2-20;
        bottomPanelHeight = ui.getWindowHeight()/2-20;
    }

    public void showMainScreen() {
        assignTitlePanel();
        ui.getContainer().add(titlePanel, "North");
        assignBottomPanel();
        ui.getContainer().add(bottomPanel, "South");
        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }

    private void assignTitlePanel() {
        titlePanel = assignJPanel(titlePanelHeight);
        JLabel title = assignJLabel(LangMan.getContent(LangFile.MainScreen, 0), 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(title);
    }

    // 4 buttons
    // Listeners: button 0, Switch Screen. button 1-3, Main Screen
    private void assignBottomPanel() {
        SwitchScreenController.SwitchScreenHandler switchScreenHandler = ui.getSwitchScreenController().getSwitchScreenHandler();
        MainScreenController.MainScreenHandler mainScreenHandler = ui.getMainScreenController().getMainScreenHandler();

        bottomPanel = assignJPanel(bottomPanelHeight);
        bottomPanel.setLayout(new GridLayout(4, 1));

        String[] texts = new String[]{
                LangMan.getContent(LangFile.MainScreen, 1),
                LangMan.getContent(LangFile.MainScreen, 2),
                LangMan.getContent(LangFile.MainScreen, 3),
                LangMan.getContent(LangFile.MainScreen, 4)
        };

        JButton gameStartButton = assignJButton(texts[0], "EnterArchiveScreen");
        gameStartButton.addActionListener(switchScreenHandler);
        bottomPanel.add(gameStartButton);
        for (int i = 1; i < texts.length; i++) {
            JButton button = assignJButton(texts[i], ""+i);
            button.addActionListener(mainScreenHandler);
            bottomPanel.add(button);
        }
    }

    // change bottom panel to change language panel
    public void assignChangeLangPanel(LangController.LangHandler langHandler) {
        ui.getContainer().remove(bottomPanel);
        bottomPanel = assignJPanel(bottomPanelHeight);
        ui.getContainer().add(bottomPanel, "South");

        JLabel description = assignJLabel(LangMan.getContent(LangFile.MainScreen, 5), 25);
        description.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(description, "North");

        JPanel selectionPanel = assignJPanel(getWindowHeight()); // change maximum possible scroll area here
        selectionPanel.setLayout(new FlowLayout());
        JScrollPane selectionScrollPane = new JScrollPane(selectionPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll(selectionScrollPane, UIObject.ScrollDirection.UP);

        selectionScrollPane.setMinimumSize(new Dimension(getWindowWidth(), getWindowHeight()/3));
        selectionScrollPane.setPreferredSize(new Dimension(getWindowWidth(), getWindowHeight()/3));

        MyInputManager inputManager = new MyInputManager("lib/Language/Pack/" + Lang.currentLanguage + "/LanguageAbbreviation.txt");
        HashMap<String, String> map = inputManager.readFileAsStringStringHashMap(" {2}");
        inputManager.closeBoth();

        for (String ab : map.keySet()) {
            JButton button = assignJButton(map.get(ab), ab);
            button.setPreferredSize(new Dimension(getWindowWidth(), getWindowHeight()/10));
            selectionPanel.add(button);
            button.addActionListener(langHandler);
        }
        bottomPanel.add(selectionScrollPane, "South");

        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }

    public void exitFromLanguageSelection() {
        ui.getContainer().remove(titlePanel);
        assignTitlePanel();
        ui.getContainer().add(titlePanel, "North");
        ui.getContainer().remove(bottomPanel);
        assignBottomPanel();
        ui.getContainer().add(bottomPanel, "South");
        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }

    public void exitMainScreen() {
        ui.getContainer().remove(titlePanel);
        ui.getContainer().remove(bottomPanel);
        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }
}
