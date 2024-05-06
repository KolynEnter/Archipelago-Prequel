package Frontend.Screens.BattleScreenPack.VisibilityManagers;

import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.BattleSystem.CardSystem.TargetSelectionManager;
import Backend.Controllers.BattleController;
import Backend.GameTrackers.BattleTracker;
import Backend.Tools.ComponentManipulator;
import Backend.Tools.Sleeper;
import Frontend.UIObject;
import Language.LangFile;
import Language.LangMan;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RecordDisplayManager extends UIObject {

    private final BattleController.BattleScreenHandler battleScreenHandler;
    private final JLayeredPane recordDisplay;
    private final JPanel multiPurposeDisplayPanel;
    private final JTextArea messageArea;
    private JPanel targetSelectionPanel;
    private final JScrollPane battleRecordScrollPane;
    private final ArrayList<Sleeper> recordMessageSleepers;

    public RecordDisplayManager(BattleController.BattleScreenHandler battleScreenHandler, JPanel multiPurposeDisplayPanel, int panelHeight) {
        recordDisplay = assignJLayeredPane(panelHeight, getWindowWidth());
        recordDisplay.setName("Record Display");
        this.multiPurposeDisplayPanel = multiPurposeDisplayPanel;
        this.battleScreenHandler = battleScreenHandler;
        recordMessageSleepers = new ArrayList<>();

        messageArea = new JTextArea();
        messageArea.setHighlighter(null);
        messageArea.setEditable(false);
        messageArea.setFont(getFont());
        messageArea.setBackground(Color.black);
        messageArea.setForeground(Color.white);
        messageArea.setBorder(null);
        messageArea.setFocusable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);

        battleRecordScrollPane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll(battleRecordScrollPane, UIObject.ScrollDirection.UP);
        battleRecordScrollPane.setBounds(100, 0, getWindowWidth()-200 ,recordDisplay.getHeight());
        battleRecordScrollPane.getViewport().setOpaque(false);
    }

    public void showRecordDisplay() {
        updateRecordDisplay();
    }

    private void updateRecordDisplay() {
        if (recordDisplay.getComponentCount() == 0) {
            initializeRecordDisplay();
        }
    }

    private void initializeRecordDisplay() {
        recordDisplay.removeAll();

        if (messageArea.getText().length() == 0) {
            addTextToBattleRecord(LangMan.getContent(LangFile.RecordDisplayManager, 0));
        }
        recordDisplay.add(battleRecordScrollPane, Integer.valueOf(1));
        recordDisplay.revalidate();
        recordDisplay.repaint();

        multiPurposeDisplayPanel.add(recordDisplay, "Center");
    }

    public void assignTargetSelectionConfirmationPanel(CardObject card) {
        battleRecordScrollPane.setVisible(false);
        if (targetSelectionPanel != null) {
            ((JLabel) ComponentManipulator.findJComponent(targetSelectionPanel, "WarningLabel")).setText(assignWarningText(card));
            targetSelectionPanel.setVisible(true);
            return;
        }

        targetSelectionPanel = assignJPanel(recordDisplay.getHeight());
        targetSelectionPanel.setLayout(null);
        targetSelectionPanel.setBorder(getBorder());
        targetSelectionPanel.setBounds(100, 0, getWindowWidth() - 200, recordDisplay.getHeight());
        recordDisplay.add(targetSelectionPanel);

        JButton confirmButton = assignJButton("Confirm", "ConfirmTargetSelection");
        confirmButton.addActionListener(battleScreenHandler);
        confirmButton.setFont(adjustableFont(30));
        confirmButton.setPreferredSize(new Dimension(getWindowWidth(), 34));
        confirmButton.setBounds(384, 80, 316, 50);
        confirmButton.setHorizontalAlignment(SwingConstants.CENTER);
        targetSelectionPanel.add(confirmButton);

        String warningText = assignWarningText(card);
        JLabel warningLabel = assignJLabel(warningText, 15);
        warningLabel.setName("WarningLabel");
        warningLabel.setBounds(384, 0, 316, 80);
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        targetSelectionPanel.add(warningLabel);

        targetSelectionPanel.revalidate();
        targetSelectionPanel.repaint();
    }

    private String assignWarningText(CardObject cardObject) {
        TargetSelectionManager targetSelectionManager = cardObject.getTargetSelectionManager();
        return targetSelectionManager.getSelectionText();
    }

    public void addIconToTargetSelectionPanel(ImageIcon imageIcon, String mobDescription, int index, BattleTracker battleTracker) {
        if (selectionListIsFull(battleTracker)) {
            return;
        }

        int targetSelectionCount = firstAvailableSpotInSelectionList(battleTracker);
        String[] targetSelectionList = battleTracker.getTargetSelectionList();
        targetSelectionList[targetSelectionCount] = "mob" + index;
        JButton iconButton = assignJButton("", "RemoveMobSelection");
        iconButton.setName("icon" + index);
        iconButton.addActionListener(battleScreenHandler);
        iconButton.setIcon(imageIcon);
        iconButton.setBounds(96*targetSelectionCount, 0, 96, 96);
        targetSelectionPanel.add(iconButton);

        JLabel descriptionLabel = assignJLabel(mobDescription, 15);
        descriptionLabel.setName("description" + index);
        descriptionLabel.setBounds(96*targetSelectionCount, 96, 96, 34);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        targetSelectionPanel.add(descriptionLabel);
        targetSelectionPanel.revalidate();
        targetSelectionPanel.repaint();
    }

    private boolean selectionListIsFull(BattleTracker battleTracker) {
        for (String s : battleTracker.getTargetSelectionList()) {
            if (s == null) {
                return false;
            }
        }
        return  true;
    }

    private int firstAvailableSpotInSelectionList(BattleTracker battleTracker) {
        int count = 0;
        for (String s : battleTracker.getTargetSelectionList()) {
            if (s == null) {
                return count;
            }
            count++;
        }
        return count;
    }

    public void removeSelection(int index, BattleTracker battleTracker) {
        ComponentManipulator.removeComponent(targetSelectionPanel, "icon"+index);
        ComponentManipulator.removeComponent(targetSelectionPanel, "description"+index);
        int position = -1;
        String[] targetSelectionList = battleTracker.getTargetSelectionList();
        for (int i = 0; i < targetSelectionList.length; i++) {
            if (targetSelectionList[i] != null && targetSelectionList[i].equals("mob" + index)) {
                position = i;
            }
        }

        targetSelectionList[position] = null;
        targetSelectionPanel.revalidate();
        targetSelectionPanel.repaint();
    }

    public void reshowBattleRecordScrollPane() {
        battleRecordScrollPane.setVisible(true);
        removeIconInTargetSelectionPanel();
        if (targetSelectionPanel != null) {
            targetSelectionPanel.setVisible(false);
        }
    }

    private void removeIconInTargetSelectionPanel() {
        if (targetSelectionPanel == null) {
            return;
        }
        ComponentManipulator.removeComponentsContainsGivenString(targetSelectionPanel, "icon");
        ComponentManipulator.removeComponentsContainsGivenString(targetSelectionPanel, "description");
    }

    public void addTextToBattleRecord(String text) {
        if (messageArea.getText().length() > 1000) {
            messageArea.setText("");
        }

        if (recordMessageSleepers.size() == 4) {
            recordMessageSleepers.remove(0);
            recordMessageSleepers.remove(1);
        }

        Sleeper sleeper = new Sleeper(text+"\n");
        sleeper.delayDisplayWordTimer(messageArea, 50);
        recordMessageSleepers.add(sleeper);
        if (recordMessageSleepers.size() > 1) {
            Sleeper prev = recordMessageSleepers.get(recordMessageSleepers.size() - 2);
            if (prev.getTimer().isRunning()) {
                prev.getTimer().stop();
                prev.justFinishTheLine(messageArea);
            }
        }
        sleeper.getTimer().start();
    }

    public JLayeredPane getRecordDisplay() {
        return recordDisplay;
    }
}
