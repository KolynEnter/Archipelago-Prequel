package Frontend.Screens;

import Backend.Controllers.SwitchScreenController;
import Backend.Tools.Sleeper;
import Frontend.UI;
import Frontend.UIObject;
import Language.Lang;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExploreScreen extends UIObject {

    private JPanel topPanel, bottomPanel;
    private JPanel buttonPanel;
    private JLayeredPane mainDisplayLayerPane;
    private JTextArea dialogueTextArea;

    private ArrayList<String> gameScreenFileContents;
    private int topPanelHeight, bottomPanelHeight;

    public ExploreScreen() {
        topPanelHeight = getWindowHeight()*3/4;
        bottomPanelHeight = getWindowHeight()/4;
    }

    public void showGameScreen(UI ui) {
        assignTopPanel(ui);
        assignBottomPanel(ui);
        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }

    // contains the main display, dialogue, setting button
    // Use LayeredPane to put dialogue on the top of the main display
    private void assignTopPanel(UI ui) {
        topPanel = assignJPanel(topPanelHeight);
        ui.getContainer().add(topPanel, "North");
        topPanel.setBorder(getBorder()); // debug
        assignMainDisplay2TopPanel(ui);
        assignLeftRightPanels2TopPanel();
    }

    private void assignMainDisplay2TopPanel(UI ui) {
        mainDisplayLayerPane = assignJLayeredPane(topPanelHeight, getWindowWidth()*3/4);
        topPanel.add(mainDisplayLayerPane, "Center");

        ImageIcon imageIcon = new ImageIcon("lib/Images/UI/Attack.png");
        JLabel label1= new JLabel();
        label1.setOpaque(true);
        label1.setBounds(getWindowWidth()*3/4/2-imageIcon.getIconWidth()/2, getWindowHeight()*3/4-imageIcon.getIconHeight(),
                imageIcon.getIconWidth(),imageIcon.getIconHeight());
        label1.setIcon(imageIcon);
        mainDisplayLayerPane.add(label1, Integer.valueOf(0));

        assignDialogueTextArea();
        assignNextDialogueButton();
        assignButtonPanel(ui);
    }

    private void assignDialogueTextArea() {
        dialogueTextArea = new JTextArea();
        dialogueTextArea.setBounds(getWindowWidth()/8, getWindowHeight()*8/16,
                getWindowWidth()/2, getWindowHeight()/16);
        dialogueTextArea.setOpaque(false);
        dialogueTextArea.setBorder(null);
        dialogueTextArea.setForeground(Color.white);
        dialogueTextArea.setEditable(false);
        dialogueTextArea.setFocusable(false);
        dialogueTextArea.setHighlighter(null);
        dialogueTextArea.setWrapStyleWord(true);
        dialogueTextArea.setLineWrap(true);
        Font font = adjustableFont(18);
        dialogueTextArea.setFont(font);
        dialogueTextArea.setBorder(getBorder());
        String text = "You have encountered enemies.";
        Sleeper sleeper = new Sleeper(text);
        sleeper.delayDisplayWordTimer(dialogueTextArea, 50);
        sleeper.getTimer().start();

        mainDisplayLayerPane.add(dialogueTextArea, Integer.valueOf(2));
    }

    private void assignNextDialogueButton() {
        JButton nextButton = assignJButton("Next", "NextDialogue");
        nextButton.setBounds(getWindowWidth()/8, getWindowHeight()*9/16,
                getWindowWidth()/2, getWindowHeight()/16-30);
        Font font = adjustableFont(15);
        nextButton.setBackground(getTransparentColor());
        nextButton.setFont(font);
        mainDisplayLayerPane.add(nextButton, Integer.valueOf(1));
    }

    private void assignButtonPanel(UI ui) {
        buttonPanel = assignJPanel(getWindowHeight()/16);
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setBounds(getWindowWidth()/8, getWindowHeight()*5/8,
                getWindowWidth()/2, getWindowHeight()/8);
        assignStartBattleButton(ui);
        buttonPanel.setOpaque(false);
        mainDisplayLayerPane.add(buttonPanel, Integer.valueOf(1));
    }

    private void assignStartBattleButton(UI ui) {
        SwitchScreenController.SwitchScreenHandler switchScreenHandler = ui.getSwitchScreenController().getSwitchScreenHandler();

        JButton startBattleButton = assignJButton("Fight!", "EnterBattleScreen");
        Font font = adjustableFont(18);
        startBattleButton.setFont(font);
        startBattleButton.addActionListener(switchScreenHandler);
        buttonPanel.add(assignButtonPanel(startBattleButton, 120));

        // Test purpose
        JButton startBattleButton1 = assignJButton("Fight!", "EnterBattleScreen");
        startBattleButton1.setFont(font);
        JButton startBattleButton2 = assignJButton("Fight!", "EnterBattleScreen");
        startBattleButton2.setFont(font);

        buttonPanel.add(assignButtonPanel(startBattleButton1, 120));
        buttonPanel.add(assignButtonPanel(startBattleButton2, 120));
    }

    private void assignLeftRightPanels2TopPanel() {
        JPanel leftPanel = assignJPanel(topPanelHeight);
        leftPanel.setBorder(getBorder()); // debug
        leftPanel.setPreferredSize(new Dimension(getWindowWidth()/8, topPanelHeight));
        topPanel.add(leftPanel, "West");
        JPanel rightPanel = assignJPanel(topPanelHeight);
        rightPanel.setBorder((getBorder())); // debug
        rightPanel.setPreferredSize(new Dimension(getWindowWidth()/8, topPanelHeight));
        topPanel.add(rightPanel, "East");
    }

    // contains the Map
    private void assignBottomPanel(UI ui) {
        bottomPanel = assignJPanel(bottomPanelHeight);
        ui.getContainer().add(bottomPanel, "South");
        bottomPanel.setBorder(getBorder()); // debug
    }

    public void exitGameScreen(UI ui) {
        ui.getContainer().remove(topPanel);
        ui.getContainer().remove(bottomPanel);
        ui.getContainer().revalidate();
        ui.getContainer().repaint();
    }
}
