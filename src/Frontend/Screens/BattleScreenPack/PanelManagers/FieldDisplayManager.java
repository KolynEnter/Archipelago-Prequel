package Frontend.Screens.BattleScreenPack.PanelManagers;

import Backend.Characters.GameObject;
import Frontend.UI;
import Frontend.UIObject;

import javax.swing.*;

public class FieldDisplayManager extends UIObject {

    private final UI ui;
    private JLayeredPane fieldDisplayPanel;
    private final int topPanelHeight;
    private final GameObject[] mobsOnField;

    public FieldDisplayManager(UI ui, int topPanelHeight, GameObject[] mobsOnField) {
        this.ui = ui;
        this.topPanelHeight = topPanelHeight;
        this.mobsOnField = mobsOnField;
        assignFieldDisplay();
    }

    public void assignFieldDisplay() {
        fieldDisplayPanel = assignJLayeredPane(topPanelHeight, getWindowWidth()*7/9);
        new BattleFieldUI(fieldDisplayPanel, ui.getBattleController().getBattleScreenHandler(), mobsOnField);
    }

    public JLayeredPane getFieldDisplayPanel() {
        return fieldDisplayPanel;
    }
}
