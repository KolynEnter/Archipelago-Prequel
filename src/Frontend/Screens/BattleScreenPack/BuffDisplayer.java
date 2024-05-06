package Frontend.Screens.BattleScreenPack;

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.Characters.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BuffDisplayer {

    public BuffDisplayer() {

    }

    public void addBuffIconToPanel(JPanel panel, BuffObject buffObject) {
        int newIconPositionIndex = panel.getComponentCount();
        buffObject.setIndexInPanel(newIconPositionIndex);
        Rectangle newIconPosition = new Rectangle(newIconPositionIndex*21, 0, 21, 21);
        JLabel label1= new JLabel();
        label1.setOpaque(true);
        label1.setIcon(buffObject.getBuffIcon());
        label1.setBounds(newIconPosition);
        panel.add(label1);
        if (panel.getComponentCount() > 4) {
            panel.setPreferredSize(new Dimension(panel.getComponentCount() * 21, 21));
        } else {
            panel.setPreferredSize(new Dimension(96, 21));
        }
        panel.revalidate();
        panel.repaint();
    }

    public void removeBuffIconInPanel(GameObject mob, int index) {
        JPanel panel = mob.getBuffPanel();

        JComponent[] components = new JComponent[panel.getComponentCount() - index-1];

        for (int i = 0; i < components.length; i++) {
            components[i] = (JComponent) panel.getComponents()[i+index+1];
        }

        for (int j = index; j < panel.getComponentCount(); j++) {
            panel.remove(j);
        }

        int count = index;
        for (JComponent component : components) {
            component.setBounds(count*21, 0, 21, 21);
            panel.add(component);
            count++;
        }

        ArrayList<BuffObject> buffList = mob.getBuffList();
        // decrease indexInPanel for buffs behind the deleted one
        for (BuffObject buff : buffList) {
            if (buff.getIndexInPanel() > index) {
                buff.setIndexInPanel(buff.getIndexInPanel()-1);
            }
        }

        panel.revalidate();
        panel.repaint();
    }
}
