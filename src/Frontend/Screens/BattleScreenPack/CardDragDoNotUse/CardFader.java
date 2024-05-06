package Frontend.Screens.BattleScreenPack.CardDragDoNotUse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardFader {

    private final JLayeredPane layeredPane;
    private final String cardNameInSlot;
    private final int cardLayer;

    public CardFader(JLayeredPane layeredPane, String cardNameInSlot, int cardLayer) {
        this.layeredPane = layeredPane;
        this.cardNameInSlot = cardNameInSlot;
        this.cardLayer = cardLayer;
    }

    public void removeCardAndPlayAnimation() {
        JLabel find = null;
        for (Component component : layeredPane.getComponentsInLayer(cardLayer)) {
            JPanel panel = (JPanel) component;
            for (Component childrenInPanel : panel.getComponents()) {
                if (childrenInPanel.getName() != null && childrenInPanel.getName().equals(cardNameInSlot)) {
                    find = (JLabel) childrenInPanel;
                    break;
                }
            }
        }

        final JLabel[] label = {find};
        int cardFadeAnimationCount = 1;
        ImageIcon[] imageArray = new ImageIcon[cardFadeAnimationCount];
        for (int i = 0; i < imageArray.length; i++) {
            imageArray[i] = new ImageIcon("lib/UI/Default_Card_Frame.png");
        }
        Timer timer = new Timer(200, null);
        timer.addActionListener(new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (i < imageArray.length) {
                    label[0].setIcon(imageArray[i]);
                    i++;
                } else {
                    for (Component component : layeredPane.getComponentsInLayer(cardLayer)) {
                        JPanel panel = (JPanel) component;
                        for (Component childrenInPanel : panel.getComponents()) {
                            if (childrenInPanel.getName().equals(cardNameInSlot)) {
                                layeredPane.remove(component);
                                layeredPane.revalidate();
                                layeredPane.repaint();
                                break;
                            }
                        }
                    }
                    timer.stop();
                }
            }
        });
        timer.start();
        layeredPane.revalidate();
        layeredPane.repaint();
    }
}
