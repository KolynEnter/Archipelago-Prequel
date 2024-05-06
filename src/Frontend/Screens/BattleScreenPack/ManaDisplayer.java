package Frontend.Screens.BattleScreenPack;

import Frontend.UI;
import Frontend.UIObject;

import javax.swing.*;
import java.awt.*;

public class ManaDisplayer {

    private JPanel manaDisplayPanel;
    private final ImageIcon manaIcon = new ImageIcon("lib/Images/UI/Cost.png");
    private final Point[] manaIconPositions = new Point[] {
            new Point(0,0),
            new Point(20, 0),
            new Point(40, 0),
            new Point(60, 0),
            new Point(80, 0),
            new Point(0,20),
            new Point(20, 20),
            new Point(40, 20),
            new Point(60, 20),
            new Point(80, 20),
    };

    public ManaDisplayer(UI ui) {
        assignManaDisplayPanel(ui);
    }

    public void updateCurrentMana(int currentMana) {
        manaDisplayPanel.removeAll();

        for (int i = 0; i < currentMana; i++) {
            Rectangle position = new Rectangle(manaIconPositions[i].x, manaIconPositions[i].y, 20, 20);
            JLabel manaLabel = assignManaLabel(position);
            manaDisplayPanel.add(manaLabel);
        }

        manaDisplayPanel.revalidate();
        manaDisplayPanel.repaint();
    }

    private void assignManaDisplayPanel(UI ui) {
        manaDisplayPanel = ui.assignJPanel(40);
        manaDisplayPanel.setPreferredSize(new Dimension(100, 40));
        manaDisplayPanel.setBounds(0, ui.getWindowHeight()*3/5-200, 100, 40);
        manaDisplayPanel.setLayout(null);
    }

    private JLabel assignManaLabel(Rectangle position) {
        JLabel costLabel = new JLabel();
        costLabel.setIcon(manaIcon);
        costLabel.setPreferredSize(new Dimension(20, 20));
        costLabel.setBounds(position);
        manaDisplayPanel.add(costLabel);
        return costLabel;
    }

    public JPanel getManaDisplayPanel() {
        return manaDisplayPanel;
    }
}
