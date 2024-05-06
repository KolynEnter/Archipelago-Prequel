package Frontend;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

class CustomizedSlider {
    /*
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel content = new JPanel();
            content.setLayout(new BorderLayout());
            content.setPreferredSize(new Dimension(300, 100));
            JSlider slider = new JSlider(0, 4) {
                @Override
                public void updateUI() {
                    setUI(new CustomSliderUI(this));
                }
            };
            slider.setPaintTrack(true);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setMajorTickSpacing(1);
            slider.setMinorTickSpacing(0);
            slider.setSnapToTicks(true);

            Font temp = null;
            try {
                temp = Font.createFont(Font.TRUETYPE_FONT, new File("src/Frontend/zpix.ttf"));
                temp = temp.deriveFont(Font.PLAIN, 15);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
            slider.setFont(temp);
            slider.setForeground(Color.white);

            Hashtable<Integer, JLabel> labels = new Hashtable<>();
            JLabel label = new JLabel("Freezing");
            label.setForeground(Color.white);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(temp);
            labels.put(0, label);
            labels.put(1, label);
            labels.put(2, label);
            labels.put(3, label);
            slider.setLabelTable(labels);

            content.add(slider, "Center");

            JFrame frame = new JFrame();
            frame.setContentPane(content);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
     */

    public static class CustomSliderUI extends BasicSliderUI {

        private static final int TRACK_HEIGHT = 8;
        private static final int TRACK_WIDTH = 8;
        private static final int TRACK_ARC = 5;
        private static final Dimension THUMB_SIZE = new Dimension(20, 20);
        private final Rectangle.Float trackShape = new Rectangle.Float();

        public CustomSliderUI(final JSlider b) {
            super(b);
        }

        @Override
        protected void calculateTrackRect() {
            super.calculateTrackRect();
            if (isHorizontal()) {
                trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
                trackRect.height = TRACK_HEIGHT;
            } else {
                trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
                trackRect.width = TRACK_WIDTH;
            }
            trackShape.setRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height);
        }

        @Override
        protected void calculateThumbLocation() {
            super.calculateThumbLocation();
            if (isHorizontal()) {
                thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
            } else {
                thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
            }
        }

        @Override
        protected Dimension getThumbSize() {
            return THUMB_SIZE;
        }

        private boolean isHorizontal() {
            return slider.getOrientation() == JSlider.HORIZONTAL;
        }

        @Override
        public void paint(final Graphics g, final JComponent c) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paint(g, c);
        }

        @Override
        public void paintTrack(final Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Shape clip = g2.getClip();

            boolean horizontal = isHorizontal();
            boolean inverted = slider.getInverted();

            // Paint shadow.
            g2.setColor(new Color(0, 0, 0));
            g2.fill(trackShape);

            // Paint track background.
            g2.setColor(new Color(0, 0, 0));
            g2.setClip(trackShape);
            trackShape.y += 1;
            g2.fill(trackShape);
            trackShape.y = trackRect.y;

            g2.setClip(clip);

            // Paint selected track.
            if (horizontal) {
                boolean ltr = slider.getComponentOrientation().isLeftToRight();
                if (ltr) inverted = !inverted;
                int thumbPos = thumbRect.x + thumbRect.width / 2;
                if (inverted) {
                    g2.clipRect(0, 0, thumbPos, slider.getHeight());
                } else {
                    g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
                }

            } else {
                int thumbPos = thumbRect.y + thumbRect.height / 2;
                if (inverted) {
                    g2.clipRect(0, 0, slider.getHeight(), thumbPos);
                } else {
                    g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
                }
            }
            g2.setColor(Color.WHITE);
            g2.fill(trackShape);
            g2.setClip(clip);
        }

        @Override
        public void paintThumb(final Graphics g) {
            g.setColor(new Color(255, 255, 255));
            g.fillRect(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintFocus(final Graphics g) {}
    }
}
