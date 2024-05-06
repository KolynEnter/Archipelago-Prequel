package Backend.Controllers;

import Frontend.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExploreScreenController {

    private final UI ui;
    private final GameScreenHandler gameSceneHandler = new GameScreenHandler();

    public ExploreScreenController(UI ui) {
        this.ui = ui;
    }

    public class GameScreenHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public GameScreenHandler getGameScreenHandler() {
        return gameSceneHandler;
    }
}
