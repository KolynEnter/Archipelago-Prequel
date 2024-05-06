package Frontend.Screens.BattleScreenPack.VisibilityManagers;

import Frontend.UIObject;

import javax.swing.*;

public class BagDisplayManager extends UIObject {

    private final JLayeredPane bagDisplay;
    private final JPanel multiPurposeDisplayPanel;

    public BagDisplayManager(JPanel multiPurposeDisplayPanel, int panelHeight) {
        this.bagDisplay = assignJLayeredPane(panelHeight, getWindowWidth());
        bagDisplay.setName("Bag Display");
        this.multiPurposeDisplayPanel = multiPurposeDisplayPanel;
    }

    public void showBagDisplay() {
        updateBagDisplay();
    }

    private void updateBagDisplay() {
        if (bagDisplay.getComponentCount() == 0) {
            initializeBagDisplay();
        }
    }

    private void initializeBagDisplay() {

        multiPurposeDisplayPanel.add(bagDisplay, "Center");
    }

    public JLayeredPane getBagDisplay() {
        return bagDisplay;
    }
}
