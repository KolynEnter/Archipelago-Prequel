package Frontend.Screens.BattleScreenPack.PanelManagers;

import Backend.GameTrackers.BattleTracker;
import Backend.Tools.ComponentManipulator;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.MultiPurposeDisplayVM;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Frontend.UI;
import Frontend.UIObject;

import javax.swing.*;
import java.awt.*;

public class MultiPurposePanelManager extends UIObject {

    private final UI ui;
    private JPanel multiPurposePanel;
    private final int bottomPanelHeight;
    private int currentArrowPosition = 0;
    private int lastCardEnemySelection = 0;
    private int lastCardAllySelection = 0;

    private MultiPurposeDisplayVM multiPurposeDisplayVM;

    public MultiPurposePanelManager(UI ui, int bottomPanelHeight) {
        this.ui = ui;
        this.bottomPanelHeight = bottomPanelHeight;
        assignMultiPurposePanel();
    }

    private void assignMultiPurposePanel() {
        int multiPurposePanelHeight = bottomPanelHeight/2 - 20;
        multiPurposePanel = assignJPanel(multiPurposePanelHeight);
        assignButtonPanel();
        assignArrowPanel();
        assignDisplayPanel();
    }

    private void assignButtonPanel() {
        JPanel buttonPanel = assignJPanel((bottomPanelHeight/2 - 20)/8);
        buttonPanel.setLayout(new GridLayout(1, 4));
        Font font = adjustableFont(18);
        String[] texts = new String[] {
                "Info",
                "Record",
                "Bag",
                "Settings"
        };
        for (int i = 0; i < 4; i++) {
            JButton button = assignJButton(texts[i], texts[i]);
            button.addActionListener(ui.getBattleController().getBattleScreenHandler());
            button.setFont(font);
            buttonPanel.add(button);
        }

        multiPurposePanel.add(buttonPanel, "Center");
    }

    private void assignArrowPanel() {
        JPanel arrowPanel = assignJPanel((bottomPanelHeight/2 - 20)/16);
        arrowPanel.setName("Arrow Panel");
        arrowPanel.setLayout(new GridLayout(1, 4));

        for (int i = 0; i < 4; i++) {
            JPanel panel = assignJPanel((bottomPanelHeight/2 - 20)/16);
            panel.setBackground(Color.black);
            arrowPanel.add(panel);
        }

        JPanel arrowLocation = (JPanel) arrowPanel.getComponent(currentArrowPosition); // 0-3
        arrowLocation.add(arrowLabel());

        multiPurposePanel.add(arrowPanel, "South");
    }

    public void changeArrowLocation(int newLocation) {
        JPanel arrowPanel = (JPanel) ComponentManipulator.findJComponent(multiPurposePanel, "Arrow Panel");
        JPanel arrowLocation = (JPanel) arrowPanel.getComponent(currentArrowPosition);
        arrowLocation.removeAll();
        arrowLocation = (JPanel) arrowPanel.getComponent(newLocation);
        arrowLocation.add(arrowLabel());
        arrowPanel.revalidate();
        arrowPanel.repaint();
        currentArrowPosition = newLocation;
    }

    private JLabel arrowLabel() {
        ImageIcon imageIcon = new ImageIcon("lib/Images/UI/Small_Arrow.png");
        JLabel label = new JLabel();
        label.setOpaque(false);
        label.setIcon(imageIcon);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

    private void assignDisplayPanel() {
        JPanel multiPurposeDisplayPanel = assignJPanel((bottomPanelHeight / 2 - 20) * 13 / 16);
        multiPurposePanel.add(multiPurposeDisplayPanel, "North");
        currentArrowPosition = 0;
        multiPurposeDisplayVM = new MultiPurposeDisplayVM(ui.getBattleController().getBattleScreenHandler(), multiPurposeDisplayPanel, (bottomPanelHeight/2 - 20)*13/16);
        multiPurposeDisplayVM.showInfoDisplay();
    }

    public void forceToShowTargetSelection(int cardIndex, BattleTracker battleTracker) {
        battleTracker.setCardIndex(cardIndex);
        CardsPanelManager cardsPanelManager = ui.getBattleScreen().getCardsPanelManager();
        multiPurposeDisplayVM.showRecordDisplay();
        changeArrowLocation(1); // Record
        battleTracker.setLastUsedCard(cardsPanelManager.getHandCardTracker()[cardIndex]);

        lastCardEnemySelection = battleTracker.getLastUsedCard().getTargetSelectionManager().getEnemySelection();
        lastCardAllySelection = battleTracker.getLastUsedCard().getTargetSelectionManager().getAllySelection();

        multiPurposeDisplayVM.getRecordDisplayManager().assignTargetSelectionConfirmationPanel(battleTracker.getLastUsedCard());
        battleTracker.setSomeCardClicked(true);
    }

    public void addMobToTargetSelection(ImageIcon icon, int mobIndex, BattleTracker battleTracker) {
        RecordDisplayManager recordDisplayManager = multiPurposeDisplayVM.getRecordDisplayManager();
        if (!targetSelectionPanelContainsThisMob(mobIndex, battleTracker)) {
            if (isQualifiedForSelection(mobIndex)) {
                if (mobIndex <= 2) {
                    recordDisplayManager.addIconToTargetSelectionPanel(icon,
                            "Enemy" + mobIndex, mobIndex, battleTracker);
                    lastCardEnemySelection--;
                } else {
                    recordDisplayManager.addIconToTargetSelectionPanel(icon,
                            "Ally" + mobIndex, mobIndex, battleTracker);
                    lastCardAllySelection--;
                }
            }
        } else {
            removeSelection(mobIndex, battleTracker);
        }
    }

    private boolean targetSelectionPanelContainsThisMob(int index, BattleTracker battleTracker) {
        for (String s : battleTracker.getTargetSelectionList()) {
            if (s != null && s.equals("mob" + index)) {
                return true;
            }
        }
        return false;
    }

    private boolean isQualifiedForSelection(int mobIndex) {
        if (mobIndex <= 2 && lastCardEnemySelection > 0) {
            return true;
        }
        return mobIndex > 2 && lastCardAllySelection > 0;
    }

    public void removeSelection(int mobIndex, BattleTracker battleTracker) {
        if (mobIndex <= 2) {
            lastCardEnemySelection++;
        } else {
            lastCardAllySelection++;
        }

        multiPurposeDisplayVM.getRecordDisplayManager().removeSelection(mobIndex, battleTracker);
    }

    public void resetSelection(BattleTracker battleTracker) {
        lastCardEnemySelection = 0;
        lastCardAllySelection = 0;
        battleTracker.resetCardClickAndSelection();
    }

    public boolean isSelectionSatisfied() {
        return lastCardEnemySelection == 0 && lastCardAllySelection == 0;
    }

    public JPanel getMultiPurposePanel() {
        return multiPurposePanel;
    }
    public MultiPurposeDisplayVM getMultiPurposeDisplayVM() {
        return multiPurposeDisplayVM;
    }
}
