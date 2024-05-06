package Frontend.Screens;

import Backend.ArchiveSystem.GameSettingStorage;
import Backend.Controllers.ArchiveScreenController;
import Backend.Controllers.SwitchScreenController;
import Backend.FileManagers.MyInputManager;
import Frontend.UI;
import Frontend.UIObject;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class ArchiveScreen extends UIObject {

    private final UI ui;
    private final int totalPossibleSaves = 3;
    private boolean[] inUseSaveSlots = new boolean[totalPossibleSaves];
    private JPanel archiveTitlePanel;
    private JPanel bottomPanel;
    private JSlider gameDifficultySlider;
    private JSlider mapSizeSlider;

    private JButton modeButton;
    private int newSaveIndex; // used for later confirmation.

    public ArchiveScreen(UI ui) {
        this.ui = ui;
    }

    private void checkInUseSaveSlots() {
        MyInputManager inputManager = new MyInputManager("lib/LoadSave/InUseSaveSlots.txt");
        inUseSaveSlots = inputManager.readFileAsBooleanArray(inUseSaveSlots.length);
        inputManager.closeBoth();
    }

    // Choose a save
    // If a save slot is not in the InUseSaveSlots.txt, create a new one based on it
    public void showArchiveScreen() {
        checkInUseSaveSlots();
        assignTitlePanel("Choose a game save");
        assignBottomPanel();
        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }

    private void assignTitlePanel(String content) {
        archiveTitlePanel = assignJPanel(getWindowHeight() * 3/13);
        JLabel title = assignJLabel(content, 45);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        archiveTitlePanel.add(title, "Center");
        ui.getContainer().add(archiveTitlePanel, "North");
    }

    // 4 buttons: save 1, save 2, save 3, back button
    // Listeners: button 0-2, Archive. button 3, Switch Screen
    private void assignBottomPanel() {
        SwitchScreenController.SwitchScreenHandler switchScreenHandler = ui.getSwitchScreenController().getSwitchScreenHandler();
        ArchiveScreenController.ArchiveScreenHandler archiveScreenHandler = ui.getArchiveScreenController().getArchiveScreenHandler();

        bottomPanel = assignJPanel(ui.getWindowHeight() * 10/13);
        bottomPanel.setLayout(new GridLayout(totalPossibleSaves+2, 1));
        for (int i = 0; i < totalPossibleSaves; i++) {
            JPanel buttonPanel = null;
            JButton button = null;
            if (inUseSaveSlots[i]) {
                button = assignJButton("Save " + i, "Save"+i);
            } else {
                button = assignJButton("Empty " + i, "Save"+i);
            }
            button.addActionListener(switchScreenHandler);
            buttonPanel = assignButtonPanel(button, 26);
            bottomPanel.add(buttonPanel);
        }
        JButton deleteSaveButton = assignJButton("Delete an existing save", "DeleteAnExistingSave");
        deleteSaveButton.addActionListener(archiveScreenHandler);
        bottomPanel.add(deleteSaveButton);
        JButton backButton = assignJButton("Back", "EnterMainScreen");
        backButton.addActionListener(switchScreenHandler);
        JPanel backPanel = assignButtonPanel(backButton, 26);
        bottomPanel.add(backPanel);

        ui.getContainer().add(bottomPanel, "South");
    }

    // Create a new save
    public void assignCreateNewSaveScreen(int newSaveIndex) {
        this.newSaveIndex = newSaveIndex;
        ui.getContainer().remove(archiveTitlePanel);
        ui.getContainer().remove(bottomPanel);
        assignTitlePanel("Start a new game");
        assignBottomPanel4SaveSetting();
        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }

    // Bottom panel of create a new save
    private void assignBottomPanel4SaveSetting() {
        bottomPanel = assignJPanel(getWindowHeight() * 10/13);
        bottomPanel.setLayout(new GridLayout(4, 1));
        bottomPanel.add(assignGameModeSwitchButton());
        bottomPanel.add(assignGameDifficultyPanel());
        bottomPanel.add(assignMapSizePanel());
        bottomPanel.add(assignBottomButtonPanel());

        ui.getContainer().add(bottomPanel, "South");
    }

    // back and complete buttons
    private JPanel assignBottomButtonPanel() {
        ArchiveScreenController.ArchiveScreenHandler archiveScreenHandler = ui.getArchiveScreenController().getArchiveScreenHandler();

        JPanel bottomButtonPanel = assignJPanel(getWindowHeight()/13);
        JButton backButton = assignJButton("Back", "BackToSaveSelection");
        backButton.addActionListener(archiveScreenHandler);
        JPanel buttonPanel = assignJPanel(getWindowHeight()/13);
        buttonPanel.setPreferredSize(new Dimension(getWindowWidth()/2, getWindowHeight()/13));
        buttonPanel.add(backButton, "Center");
        bottomButtonPanel.add(buttonPanel, "East");

        JButton completeButton =assignJButton("Complete", "StartOneNewSave");
        completeButton.addActionListener(archiveScreenHandler);
        JPanel completePanel = assignJPanel(getWindowHeight()/13);
        completePanel.setPreferredSize(new Dimension(getWindowWidth()/2, getWindowHeight()/13));
        completePanel.add(completeButton, "Center");
        bottomButtonPanel.add(completePanel, "West");

        return bottomButtonPanel;
    }

    private JPanel assignGameDifficultyPanel() {
        Hashtable<Integer, String> table = new Hashtable<>();
        table.put(0, "Easy       ");
        table.put(1, " Hard   ");
        table.put(2, "Extreme");
        table.put(3, "   Godlike");

        Hashtable<Integer, JLabel> labels = assignLabels(table);
        gameDifficultySlider = assignSlider(labels);
        return sliderInputPanel("Game Difficulty", 3, gameDifficultySlider);
    }

    private JPanel assignMapSizePanel() {
        Hashtable<Integer, String> table = new Hashtable<>();
        table.put(0, "Small       ");
        table.put(1, "Standard");
        table.put(2, "    Large");

        Hashtable<Integer, JLabel> labels =assignLabels(table);
        mapSizeSlider = assignSlider(labels);
        return sliderInputPanel("Map Size", 2, mapSizeSlider);
    }

    private Hashtable<Integer, JLabel> assignLabels(Hashtable<Integer, String> table) {
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (Integer digit : table.keySet()) {
            JLabel label = assignJLabel(table.get(digit), 25);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            labels.put(digit, label);
        }

        return labels;
    }

    private JPanel sliderInputPanel(String title, int maximum, JSlider slider) {
        JPanel panel = assignJPanel(getWindowHeight()*2/13);

        JPanel descriptionPanel = assignJPanel(getWindowHeight()*2/13);
        descriptionPanel.setPreferredSize(new Dimension(getWindowWidth(), getWindowHeight()/13+30));
        JLabel description = assignJLabel(title, 30);
        description.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionPanel.add(description, "South");
        panel.add(descriptionPanel, "North");

        JPanel sliderPanel = assignJPanel(getWindowHeight()*2/13);
        sliderPanel.setPreferredSize(new Dimension(getWindowWidth()/2, getWindowHeight()/13));

        slider.setMaximum(maximum);
        slider.setBackground(Color.black);
        slider.setPreferredSize(new Dimension(getWindowWidth()/2, getWindowHeight()/13));
        sliderPanel.add(slider, "Center");
        JPanel filler1 = assignJPanel(getWindowHeight()/13);
        filler1.setPreferredSize(new Dimension(getWindowWidth()/5, getWindowHeight()/13));
        sliderPanel.add(filler1, "East");
        JPanel filler2 = assignJPanel(getWindowHeight()/13);
        filler2.setPreferredSize(new Dimension(getWindowWidth()/5, getWindowHeight()/13));
        sliderPanel.add(filler2, "West");
        panel.add(sliderPanel, "South");
        panel.setBorder(null);

        return panel;
    }

    private JPanel assignGameModeSwitchButton() {
        ArchiveScreenController.ArchiveScreenHandler archiveScreenHandler = ui.getArchiveScreenController().getArchiveScreenHandler();

        JPanel panel = assignJPanel(getWindowHeight()/13);
        modeButton = assignJButton("-> RogueLike", "GameModeSwitch");
        modeButton.addActionListener(archiveScreenHandler);
        panel.add(modeButton, "South");

        JPanel supportPanel = assignJPanel(getWindowHeight()/13);
        JLabel gameModeLabel = assignJLabel("Game Mode", 30);
        gameModeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel filler = assignJPanel(getWindowHeight()/13+10);
        supportPanel.add(filler, "North");
        supportPanel.add(gameModeLabel, "Center");

        panel.add(supportPanel, "Center");

        return panel;
    }

    public void exitArchiveScreen() {
        ui.getContainer().remove(archiveTitlePanel);
        ui.getContainer().remove(bottomPanel);
        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }

    // Read Game Mode from Archive Screen Controller
    public GameSettingStorage storeGameSetting() {
        // Need input from game mode, game difficulty, map size, default is 0
        return new GameSettingStorage(0,
                gameDifficultySlider.getValue(),
                mapSizeSlider.getValue());
    }

    public boolean[] getInUseSaveSlots() {
        return inUseSaveSlots;
    }
    public JButton getModeButton() {
        return modeButton;
    }
    public int getNewSaveIndex() {
        return newSaveIndex;
    }
}
