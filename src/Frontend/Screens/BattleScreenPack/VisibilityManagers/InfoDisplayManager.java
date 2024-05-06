package Frontend.Screens.BattleScreenPack.VisibilityManagers;

import Frontend.UIObject;

import javax.swing.*;

public class InfoDisplayManager extends UIObject {

    private final JLayeredPane infoDisplay;
    private final JPanel multiPurposeDisplayPanel;

    public InfoDisplayManager(JPanel multiPurposeDisplayPanel, int panelHeight) {
        infoDisplay = assignJLayeredPane(panelHeight, getWindowWidth());
        infoDisplay.setName("Info Display");
        this.multiPurposeDisplayPanel = multiPurposeDisplayPanel;
    }

    // Info can have
    // 1. display information about mobs
    // 2.

    public void showInfoDisplay() {
        updateInfoDisplay();
    }

    private void updateInfoDisplay() {
        if (infoDisplay.getComponentCount() == 0) {
            initializeInfoDisplay();
        }
    }

    private void initializeInfoDisplay() {
        //JLabel label = ui.assignJLabel("Info", 50);
        //infoDisplay.add(label);


        multiPurposeDisplayPanel.add(infoDisplay, "Center");
    }

    public JLayeredPane getInfoDisplay() {
        return infoDisplay;
    }
}
