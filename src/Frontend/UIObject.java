package Frontend;

//import Language.Lang;

import Language.Lang;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Hashtable;

public class UIObject extends Lang {

    private final int windowWidth = 900;
    private final int windowHeight = 900;
    private final Font font = assignFont();
    private final DecimalFormat df = new DecimalFormat("0.0");
    private final LineBorder border = new LineBorder(Color.white, 1);


    public JPanel assignJPanel(int height) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setPreferredSize(new Dimension(windowWidth, height));
        panel.setLayout(new BorderLayout());
        //panel.setBorder(border);
        return panel;
    }

    // How to add: pane.add(label1, Integer.valueOf(0));
    public JLayeredPane assignJLayeredPane(int height, int width) {
        JLayeredPane pane = new JLayeredPane();
        pane.setBackground(Color.black);
        pane.setBounds(0, 0, width, height);
        return pane;
    }

    public JLabel assignJLabel(String content, int fontSize) {
        Font tempFont = font.deriveFont(Font.PLAIN, fontSize);
        JLabel jLabel = new JLabel(content);
        jLabel.setForeground(Color.white);
        jLabel.setFont(tempFont);

        return jLabel;
    }

    public JButton assignJButton(String text, String command) {
        JButton jButton = new JButton(text);
        jButton.setBackground(Color.black);
        jButton.setForeground(Color.white);
        jButton.setFont(font);
        jButton.setOpaque(true);
        jButton.setBorderPainted(false);
        //jButton.addActionListener(ch); add this when needed
        jButton.setActionCommand(command);
        jButton.setFocusPainted(false);
        jButton.setContentAreaFilled(false);

        return jButton;
    }

    public JPanel assignButtonPanel(JButton button, int rate) {
        JPanel buttonPanel = assignJPanel(getWindowHeight() * 2/(rate/2));
        buttonPanel.setOpaque(false);
        JPanel filler1 = assignJPanel(getWindowHeight()/rate);
        filler1.setBorder(null);
        filler1.setOpaque(false);
        JPanel filler2 = assignJPanel(getWindowHeight()/rate);
        filler2.setBorder(null);
        filler2.setOpaque(false);
        buttonPanel.add(filler1, "North");
        buttonPanel.add(button, "Center");
        buttonPanel.add(filler2, "South");

        return buttonPanel;
    }

    public JFrame assignWindow() {
        JFrame window = new JFrame();
        window.setSize(windowWidth, windowHeight);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(new BorderLayout());
        window.setResizable(false);

        return window;
    }

    public Container assignContainer(JFrame window) {
        Container container = window.getContentPane();
        container.setPreferredSize(new Dimension(windowWidth, windowHeight));

        return container;
    }

    private Font assignFont() {
        Font temp = null;
        try {
            temp = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("Frontend/zpix.ttf"));
            temp = temp.deriveFont(Font.PLAIN, 25);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public Font adjustableFont(int fontSize) {
        return font.deriveFont(Font.PLAIN, fontSize);
    }

    public void scroll(JScrollPane scrollPane, ScrollDirection direction) {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new CustomizedScrollbar.MyScrollbarUI());
        // If we want to scroll to the top, set this value to the minimum,
        // else to the maximum
        int topOrBottom = direction == ScrollDirection.UP ?
                verticalBar.getMinimum() :
                verticalBar.getMaximum();

        AdjustmentListener scroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(topOrBottom);
                // We have to remove the listener, otherwise the
                // user would be unable to scroll afterwards
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(scroller);
    }

    public void scrollHorizontal(JScrollPane scrollPane, ScrollDirection direction) {
        JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
        horizontalBar.setUI(new CustomizedScrollbar.MyScrollbarUI());
        // If we want to scroll to the top, set this value to the minimum,
        // else to the maximum
        int leftOrRight = direction == ScrollDirection.LEFT ?
                horizontalBar.getMinimum() :
                horizontalBar.getMaximum();

        AdjustmentListener scroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(leftOrRight);
                // We have to remove the listener, otherwise the
                // user would be unable to scroll afterwards
                horizontalBar.removeAdjustmentListener(this);
            }
        };
        horizontalBar.addAdjustmentListener(scroller);
    }

    public enum ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }

    public JSlider assignSlider(Hashtable<Integer, JLabel> labels) {
        JSlider slider = new JSlider(0, labels.size()) {
            @Override
            public void updateUI() {
                setUI(new CustomizedSlider.CustomSliderUI(this));
            }
        };
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(0);
        slider.setSnapToTicks(true);
        slider.setLabelTable(labels);

        slider.setFont(font);
        slider.setForeground(Color.white);

        return slider;
    }

    public void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Color getTransparentColor() {
        return new Color(0f, 0f, 0f, 0f);
    }
    public LineBorder getBorder() {
        return border;
    }
    public int getWindowWidth() {
        return windowWidth;
    }
    public int getWindowHeight() {
        return windowHeight;
    }
    public Font getFont() {
        return font;
    }
    public DecimalFormat getDf() {
        return df;
    }
}
