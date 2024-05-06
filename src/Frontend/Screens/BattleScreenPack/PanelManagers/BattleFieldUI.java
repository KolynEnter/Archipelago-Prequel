package Frontend.Screens.BattleScreenPack.PanelManagers;

import Backend.BattleSystem.HpManager;
import Backend.Characters.GameObject;
import Backend.Controllers.BattleController;
import Frontend.CustomizedProgressBarUI;
import Frontend.UIObject;

import javax.swing.*;
import java.awt.*;

public class BattleFieldUI extends UIObject {

    private final Rectangle mob1Icon;
    private final Rectangle mob2Icon;
    private final Rectangle mob3Icon;
    private final Rectangle mob4Icon;
    private final Rectangle mob5Icon;
    private final Rectangle mob6Icon;
    private final Rectangle circleIcon;

    private final Rectangle mob1BuffBar;
    private final Rectangle mob2BuffBar;
    private final Rectangle mob3BuffBar;
    private final Rectangle mob4BuffBar;
    private final Rectangle mob5BuffBar;
    private final Rectangle mob6BuffBar;

    private final Rectangle mob1HPBar;
    private final Rectangle mob2HPBar;
    private final Rectangle mob3HPBar;
    private final Rectangle mob4SPDBar;
    private final Rectangle mob5SPDBar;
    private final Rectangle mob6SPDBar;

    private final Rectangle mob1SPDBar;
    private final Rectangle mob2SPDBar;
    private final Rectangle mob3SPDBar;
    private final Rectangle mob4HPBar;
    private final Rectangle mob5HPBar;
    private final Rectangle mob6HPBar;

    private final JLayeredPane fieldDisplay;
    private final GameObject[] mobsOnField;

    public BattleFieldUI(JLayeredPane fieldDisplay,
                         BattleController.BattleScreenHandler battleController,
                         GameObject[] mobsOnField) {
        this.fieldDisplay = fieldDisplay;
        this.mobsOnField = mobsOnField;
        int width = fieldDisplay.getWidth();
        int height = fieldDisplay.getHeight() - 20;

        mob1Icon = new Rectangle(width /8, (height -20)/10, 96, 96);
        mob2Icon = new Rectangle(width /2-48, (height -20)*3/100, 96, 96);
        mob3Icon = new Rectangle(width *7/8-96, (height -20)/10, 96, 96);
        mob4Icon = new Rectangle(width /8, height *7/10, 96, 96);
        mob5Icon = new Rectangle(width /2-48, height *10/13, 96, 96);
        mob6Icon = new Rectangle(width *7/8-96, height *7/10, 96, 96);
        circleIcon = new Rectangle(width /2-64, (height-10)/2-64, 128, 128);

        mob1BuffBar = new Rectangle(width /8,mob1Icon.y+96,96, 28);
        mob2BuffBar = new Rectangle(width /2-48,mob2Icon.y+96,96, 28);
        mob3BuffBar = new Rectangle(width *7/8-96,mob3Icon.y+96,96, 28);
        mob4BuffBar = new Rectangle(width /8,mob4Icon.y-28,96, 28);
        mob5BuffBar = new Rectangle(width /2-48,mob5Icon.y-28,96, 28);
        mob6BuffBar = new Rectangle(width *7/8-96,mob6Icon.y-28,96, 28);

        mob1HPBar = new Rectangle(width /8, mob1Icon.y+124, 96, 16);
        mob2HPBar = new Rectangle(width /2-48, mob2Icon.y+124, 96, 16);
        mob3HPBar = new Rectangle(width *7/8-96, mob3Icon.y+124, 96, 16);
        mob4SPDBar = new Rectangle(width /8, mob4Icon.y-52, 96, 8);
        mob5SPDBar = new Rectangle(width /2-48, mob5Icon.y-52, 96, 8);
        mob6SPDBar = new Rectangle(width *7/8-96, mob6Icon.y-52, 96, 8);

        mob1SPDBar = new Rectangle(width /8, mob1Icon.y+140, 96, 8);
        mob2SPDBar = new Rectangle(width /2-48, mob2Icon.y+140, 96, 8);
        mob3SPDBar = new Rectangle(width *7/8-96, mob3Icon.y+140, 96, 8);
        mob4HPBar = new Rectangle(width /8, mob4Icon.y-44, 96, 16);
        mob5HPBar = new Rectangle(width /2-48, mob5Icon.y-44, 96, 16);
        mob6HPBar = new Rectangle(width *7/8-96, mob6Icon.y-44, 96, 16);

        assignIcons(battleController);
        assignLongBars();
    }

    private void assignIcons(BattleController.BattleScreenHandler battleScreenHandler) {
        Rectangle[] iconPositions = new Rectangle[] {
                mob1Icon,
                mob2Icon,
                mob3Icon,
                mob4Icon,
                mob5Icon,
                mob6Icon
        };
        int count = 0;
        for (Rectangle rectangle : iconPositions) {
            ImageIcon imageIcon;
            if (mobsOnField[count] != null) {
                imageIcon = mobsOnField[count].getSpriteSheet();
            } else {
                imageIcon = new ImageIcon("lib/Images/UI/96*96_Rectangle.png");
            }
            JButton button = new JButton();
            button.setOpaque(true);
            button.setBounds(rectangle);
            button.setIcon(imageIcon);
            button.addActionListener(battleScreenHandler);
            button.setName("Mob" + count);
            button.setActionCommand("Some Mob");
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            fieldDisplay.add(button, Integer.valueOf(1));
            count++;
        }

        ImageIcon imageIcon = new ImageIcon("lib/Images/UI/128*128_Circle.png");
        JLabel label1= new JLabel();
        label1.setOpaque(true);
        label1.setBounds(circleIcon);
        label1.setIcon(imageIcon);
        fieldDisplay.add(label1, Integer.valueOf(1));
    }

    private void assignLongBars() {
        Rectangle[] buffBars = new Rectangle[] {
                mob1BuffBar,
                mob2BuffBar,
                mob3BuffBar,
                mob4BuffBar,
                mob5BuffBar,
                mob6BuffBar,
        };

        int count = 0;
        for (Rectangle rectangle : buffBars) {
            if (mobsOnField[count] == null) {
                count++;
                continue;
            }
            JPanel panel = assignJPanel(0);
            panel.setLayout(null);
            panel.setPreferredSize(rectangle.getSize());
            mobsOnField[count].setBuffPanel(panel);

            JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollHorizontal(scrollPane, UIObject.ScrollDirection.LEFT);

            scrollPane.setBounds(rectangle);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(10, 4));

            fieldDisplay.add(scrollPane, Integer.valueOf(1));
            count++;
        }

        Rectangle[] hpBars = new Rectangle[] {
                mob1HPBar,
                mob2HPBar,
                mob3HPBar,
                mob4HPBar,
                mob5HPBar,
                mob6HPBar
        };

        count = 0;
        for (Rectangle rectangle : hpBars) {
            if (mobsOnField[count] == null) {
                count++;
                continue;
            }
            JProgressBar bar = new JProgressBar(0, 100);
            float percent = new HpManager().calculateHealthPointPercent(mobsOnField[count]);
            bar.setValue((int) percent);
            bar.setBounds(rectangle);
            bar.setStringPainted(true);
            bar.setFont(adjustableFont(12));
            bar.setUI(new CustomizedProgressBarUI(Color.red, Color.white));
            bar.setString("HP: " + getDf().format(percent) + "%");
            bar.setBackground(Color.BLACK);
            mobsOnField[count].setHpBar(bar);
            fieldDisplay.add(bar, Integer.valueOf(1));
            count++;
        }

        Rectangle[] speedBars = new Rectangle[] {
                mob1SPDBar,
                mob2SPDBar,
                mob3SPDBar,
                mob4SPDBar,
                mob5SPDBar,
                mob6SPDBar
        };

        count = 0;
        for (Rectangle rectangle : speedBars) {
            if (mobsOnField[count] == null) {
                count++;
                continue;
            }
            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(0);
            bar.setBounds(rectangle);
            bar.setStringPainted(false);
            bar.setFont(adjustableFont(12));
            bar.setUI(new CustomizedProgressBarUI(Color.cyan, Color.black));
            //bar.setString("0/100");
            bar.setBackground(Color.BLACK);
            mobsOnField[count].setValueInMovementBar(0);
            mobsOnField[count].setMovementBar(bar);
            fieldDisplay.add(bar, Integer.valueOf(1));
            count++;
        }
    }
}
