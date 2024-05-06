package Frontend.Screens.BattleScreenPack.PanelManagers;

import Backend.BattleSystem.CardSystem.CardDealer;
import Backend.BattleSystem.CardSystem.Cards.BuffCards.PoisonAttack;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Frontend.UI;
import Frontend.UIObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CardsPanelManager extends UIObject{

    private final UI ui;
    private JLayeredPane cardsPanel;
    private JScrollPane cardsScrollPane;
    private final int bottomPanelHeight;
    private final CardObject[] handCardTracker = new CardObject[10];

    public CardsPanelManager(UI ui, int bottomPanelHeight) {
        this.ui = ui;
        this.bottomPanelHeight = bottomPanelHeight;
        assignCardsPanel();
    }

    private void assignCardsPanel() {
        // the 20 is for scroll bar
        int cardsPanelHeight = bottomPanelHeight/2 + 20;
        cardsPanel = assignJLayeredPane(cardsPanelHeight, (cardsPanelHeight-13)*70/9);
        cardsPanel.setBackground(Color.black);
        cardsPanel.setPreferredSize(new Dimension((cardsPanelHeight-13)*70/9, cardsPanelHeight));
        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        refreshCardsPanel();

        cardsScrollPane = new JScrollPane(cardsPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollHorizontal(cardsScrollPane, UIObject.ScrollDirection.LEFT);

        cardsScrollPane.setBackground(getTransparentColor());
        cardsScrollPane.getViewport().setOpaque(false);
        cardsScrollPane.setMinimumSize(new Dimension(getWindowWidth(), cardsPanelHeight));
        cardsScrollPane.setPreferredSize(new Dimension(getWindowWidth(), cardsPanelHeight));
    }

    public void refreshCardsPanel() {
        cardsPanel.removeAll();
        int cardsPanelHeight = bottomPanelHeight/2 + 20;

        for (int i = 0; i < handCardTracker.length; i++) {
            JPanel testPanel = assignJPanel(cardsPanelHeight);
            testPanel.setPreferredSize(new Dimension((cardsPanelHeight-20)*7/9, cardsPanelHeight-20));
            JButton cardButton = null;
            if (handCardTracker[i] != null) {
                cardButton = cardClickAreaBoxLabel(handCardTracker[i]);
            } else {
                cardButton = defaultCardFrameButton();
            }
            cardButton.setName("Card" + i);
            testPanel.add(cardButton, "Center");
            cardsPanel.add(testPanel, Integer.valueOf(1));
        }
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private JButton defaultCardFrameButton() {
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(ImageIO.read(new File("lib/Images/UI/Default_Card_Frame.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton button = assignJButton("", "Empty Card");
        button.setOpaque(false);
        button.setIcon(imageIcon);
        button.setHorizontalAlignment(SwingConstants.CENTER);

        return button;
    }

    private JButton cardClickAreaBoxLabel(CardObject card) {
        ImageIcon imageIcon = card.getCardSprite();
        JButton button = assignJButton("", "Some Card");
        button.setOpaque(false);
        button.setIcon(imageIcon);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.addActionListener(ui.getBattleController().getBattleScreenHandler());

        return button;
    }

    public JLayeredPane getCardsPanel() {
        return cardsPanel;
    }
    public JScrollPane getCardsScrollPane() {
        return cardsScrollPane;
    }
    public CardObject[] getHandCardTracker() {
        return handCardTracker;
    }
}
