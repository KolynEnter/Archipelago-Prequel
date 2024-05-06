package Frontend;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class CustomizedProgressBarUI extends BasicProgressBarUI {

    private final Color frontColor;
    private final Color backColor;

    public CustomizedProgressBarUI(Color frontColor, Color backColor) {
        super();
        this.frontColor = frontColor;
        this.backColor = backColor;
    }

    @Override
    protected Color getSelectionBackground() { return frontColor; }
    @Override
    protected Color getSelectionForeground() { return backColor; }

    @Override
    public void paintDeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth  = progressBar.getWidth()  - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }
        int cellLength = getCellLength();
        int cellSpacing = getCellSpacing();
        // amount of progress to draw
        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

        if(progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
            // draw the cells
            float x = amountFull / (float)barRectWidth;
            //g.setColor(getColorFromPallet(pallet, x));
            g.setColor(frontColor);
            g.fillRect(b.left, b.top, amountFull, barRectHeight);
        } else { // VERTICAL
            //...
        }
        // Deal with possible text painting
        if(progressBar.isStringPainted()) {
            paintString(g, b.left, b.top, barRectWidth, barRectHeight, amountFull, b);
        }
    }
}
