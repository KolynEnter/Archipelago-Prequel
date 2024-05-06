package Frontend.Screens.BattleScreenPack.VisibilityManagers;

import Frontend.UIObject;

import javax.swing.*;

public class SettingsDisplayManager extends UIObject {

    private final JLayeredPane settingsDisplay;
    private final JPanel multiPurposeDisplayPanel;

    public SettingsDisplayManager(JPanel multiPurposeDisplayPanel, int panelHeight) {
        this.settingsDisplay = assignJLayeredPane(panelHeight, getWindowWidth());
        settingsDisplay.setName("Settings Display");
        this.multiPurposeDisplayPanel = multiPurposeDisplayPanel;
    }

    public void showSettingsDisplay() {
        updateSettingsDisplay();
    }

    private void updateSettingsDisplay() {
        if (settingsDisplay.getComponentCount() == 0) {
            initializeSettingsDisplay();
        }
    }

    private void initializeSettingsDisplay() {

        multiPurposeDisplayPanel.add(settingsDisplay, "Center");
    }

    public JLayeredPane getSettingsDisplay() {
        return settingsDisplay;
    }
}
