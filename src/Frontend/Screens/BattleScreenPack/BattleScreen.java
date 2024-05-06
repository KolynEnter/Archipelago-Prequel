package Frontend.Screens.BattleScreenPack;

import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.PanelManagers.CardsPanelManager;
import Frontend.Screens.BattleScreenPack.PanelManagers.FieldDisplayManager;
import Frontend.Screens.BattleScreenPack.PanelManagers.MultiPurposePanelManager;
import Frontend.UI;
import Frontend.UIObject;
import Language.Lang;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BattleScreen extends UIObject {

    private final UI ui;
    // top contains the field
    // bottom contains the cards and multiPurpose
    private JPanel topPanel, bottomPanel;

    private final int topPanelHeight;
    private final int bottomPanelHeight;

    private final ManaDisplayer manaDisplayer;

    private CardsPanelManager cardsPanelManager;
    private MultiPurposePanelManager multiPurposePanelManager;
    private FieldDisplayManager fieldDisplayManager;

    private final ArrayList<String> fileContents;

    public BattleScreen(UI ui) {
        this.ui = ui;
        manaDisplayer = new ManaDisplayer(ui);
        topPanelHeight = ui.getWindowHeight()*3/5;
        bottomPanelHeight = ui.getWindowHeight()*2/5;

        fileContents = Lang.readFileContents("/BattleScreen.txt");
    }

    public void showBattleScreen(GameObject[] mobsOnField) {
        assignTopPanel(mobsOnField);
        assignBottomPanel();
        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }

    private void assignTopPanel(GameObject[] mobsOnField) {
        fieldDisplayManager = new FieldDisplayManager(ui, topPanelHeight, mobsOnField);
        topPanel = assignJPanel(topPanelHeight);
        assignFiller2TopPanel();
        topPanel.add(fieldDisplayManager.getFieldDisplayPanel(), "Center");
        ui.getContainer().add(topPanel, "Center");
    }

    private void assignFiller2TopPanel() {
        JPanel filler1 = assignJPanel(topPanelHeight);
        filler1.setPreferredSize(new Dimension(getWindowWidth()/8, topPanelHeight));

        JPanel filler2 = assignJPanel(topPanelHeight);
        filler2.setPreferredSize(new Dimension(getWindowWidth()/8, topPanelHeight));
        filler2.setLayout(null);

        filler1.setBorder(getBorder());
        filler2.setBorder(getBorder());

        // Add turn end button to filler 2
        JButton turnEndButton = assignJButton("<html>Turn End", "Turn End");
        turnEndButton.addActionListener(ui.getBattleController().getBattleScreenHandler());
        turnEndButton.setPreferredSize(new Dimension(getWindowWidth()/9, topPanelHeight/4));
        turnEndButton.setBounds(0, topPanelHeight*3/4, getWindowWidth()/9, topPanelHeight/4);
        filler2.add(turnEndButton);

        manaDisplayer.updateCurrentMana(1);
        filler2.add(manaDisplayer.getManaDisplayPanel());

        topPanel.add(filler1, "West");
        topPanel.add(filler2, "East");
    }

    private void assignBottomPanel() {
        cardsPanelManager = new CardsPanelManager(ui, bottomPanelHeight);
        multiPurposePanelManager = new MultiPurposePanelManager(ui, bottomPanelHeight);
        bottomPanel = assignJPanel(bottomPanelHeight);
        bottomPanel.add(cardsPanelManager.getCardsScrollPane(), "Center");
        bottomPanel.add(multiPurposePanelManager.getMultiPurposePanel(), "South");
        ui.getContainer().add(bottomPanel, "South");
    }

    public ManaDisplayer getManaDisplayer() {
        return manaDisplayer;
    }
    public CardsPanelManager getCardsPanelManager() {
        return cardsPanelManager;
    }
    public MultiPurposePanelManager getMultiPurposePanelManager() {
        return multiPurposePanelManager;
    }
    public FieldDisplayManager getFieldDisplayManager() {
        return fieldDisplayManager;
    }
}
