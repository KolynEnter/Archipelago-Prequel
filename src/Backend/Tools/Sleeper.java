package Backend.Tools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sleeper {
    private int i = 0;
    private final String text;
    private Timer timer;

    public Sleeper(String text) {
        this.text = text;
    }

    public void delayDisplayWordTimer(JTextArea area, int displaySpeed) {
        timer = new Timer (displaySpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] characters = text.toCharArray();
                if(i < characters.length) {
                    area.append(String.valueOf(characters[i]));
                } else {
                    Timer thisTimer = (Timer) e.getSource();
                    thisTimer.stop();
                }
                i++;
            }
        });
    }

    public void justFinishTheLine(JTextArea area) {
        area.append(text.substring(i));
    }

    public Timer getTimer() {
        return timer;
    }
}
