package Frontend.Screens.BattleScreenPack.CardDragDoNotUse;

import Frontend.UI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardLabelDragHandler extends MouseAdapter {

    private Point offset;
    private final Rectangle triggerArea;
    private final UI ui;
    private final CardFader cardFader;

    public CardLabelDragHandler(Rectangle triggerArea, UI ui, CardFader cardFader) {
        this.triggerArea = triggerArea;
        this.ui = ui;
        this.cardFader = cardFader;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        offset = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getPoint().x - offset.x;
        int y = e.getPoint().y - offset.y;
        Component component = e.getComponent();
        Point topLeft = component.getLocation();
        topLeft.x += x;
        topLeft.y += y;
        Point downRight = new Point((int)(topLeft.getX() + e.getComponent().getWidth()),
                (int)(topLeft.getY() + e.getComponent().getHeight()));
        keepImageInBounds(e.getComponent(), topLeft, downRight, ui.getWindowWidth(), ui.getWindowHeight());
        component.setLocation(topLeft);
    }

    private void keepImageInBounds(Component host, Point topLeft, Point downRight, int boundWidth, int boundHeight) {
        if (topLeft.x > boundWidth) {
            topLeft.x = boundWidth;
        }
        if (topLeft.x < 0) {
            topLeft.x = 0;
        }
        if (topLeft.y > boundHeight) {
            topLeft.y = boundHeight;
        }
        if (topLeft.y < 0) {
            topLeft.y = 0;
        }
        if (downRight.x > boundWidth) {
            topLeft.x = boundWidth - host.getWidth();
        }
        if (downRight.x < 0) {
            topLeft.x = 0;
        }
        if (downRight.y > boundHeight) {
            topLeft.y = boundWidth - host.getHeight();
        }
        if (downRight.y < 0) {
            topLeft.y = 0;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (withinTriggerArea(e.getComponent(), e.getComponent().getLocation())) {
            //cardFader.removeCardAndPlayAnimation();
        }
    }

    private boolean withinTriggerArea(Component host, Point itemLocation) {
        Point topLeft = itemLocation.getLocation();
        Point topRight = new Point((int)(topLeft.getX() + host.getWidth()), (int)(topLeft.getY()));
        Point downLeft = new Point((int)(topLeft.getX()), (int)(topLeft.getY() + host.getHeight()));
        Point downRight = new Point((int)(topLeft.getX() + host.getWidth()),
                (int)(topLeft.getY() + host.getHeight()));
        return triggerArea.contains(topLeft) ||
                triggerArea.contains(topRight) ||
                triggerArea.contains(downLeft) ||
                triggerArea.contains(downRight);
    }
}
