package Frontend;

import Backend.Controllers.*;
import Backend.GameCore;
import Backend.GameTrackers.GameTracker;
import Frontend.Screens.MainScreen;

import Frontend.Screens.ArchiveScreen;
import Frontend.Screens.BattleScreenPack.BattleScreen;
import Frontend.Screens.ExploreScreen;

import javax.swing.*;
import java.awt.*;

public class UI extends UIObject {

    private final GameCore core;
    private Container container;

    private final MainScreen mainScreen;
    private final ArchiveScreen archiveScreen;
    private final ExploreScreen exploreScreen;
    private final BattleScreen battleScreen;

    public UI(GameCore core) {
        this.core = core;
        mainScreen = new MainScreen(this);
        archiveScreen = new ArchiveScreen(this);
        exploreScreen = new ExploreScreen();
        battleScreen = new BattleScreen(this);
        assignBaseScreen();
    }

    private void assignBaseScreen() {
        setLookAndFeel();
        JFrame window = assignWindow();
        container = assignContainer(window);
        window.setVisible(true);
    }

    public void assignMainScreen() {
        mainScreen.showMainScreen();
        container.revalidate();
        container.repaint();
    }

    public Container getContainer() {
        return container;
    }
    public GameTracker getGameTracker() {return core.getGameTracker();}

    public ArchiveScreenController getArchiveScreenController() {
        return core.getArchiveScreenController();
    }
    public BattleController getBattleController() {
        return core.getBattleController();
    }
    public SwitchScreenController getSwitchScreenController() {
        return core.getSwitchScreenController();
    }
    public MainScreenController getMainScreenController() {
        return core.getMainScreenController();
    }
    public ExploreScreenController getExploreScreenController() {
        return core.getExploreScreenController();
    }

    public ArchiveScreen getArchiveScreen() {
        return archiveScreen;
    }
    public BattleScreen getBattleScreen() {
        return battleScreen;
    }
    public ExploreScreen getExploreScreen() {
        return exploreScreen;
    }
    public MainScreen getMainScreen() {
        return mainScreen;
    }
}
