package Backend;

import Backend.Controllers.*;
import Backend.GameTrackers.GameTracker;
import Frontend.UI;
import Language.LangMan;

public class GameCore {

    private final UI ui = new UI(this);
    private final GameTracker gameTracker = new GameTracker(ui);

    private final MainScreenController mainScreenController = new MainScreenController(ui);
    private final ArchiveScreenController archiveScreenController = new ArchiveScreenController(ui);
    private final ExploreScreenController exploreScreenController = new ExploreScreenController(ui);
    private final BattleController battleController = new BattleController(this);
    private final SwitchScreenController switchScreenController = new SwitchScreenController(this);

    public GameCore() {

    }

    public void startGame() {
        new LangMan().loadAllLanguagePacks();
        ui.assignMainScreen();
    }


    public UI getUI() {
        return ui;
    }
    public GameTracker getGameTracker() {
        return gameTracker;
    }
    public MainScreenController getMainScreenController() {
        return mainScreenController;
    }
    public ArchiveScreenController getArchiveScreenController() {
        return archiveScreenController;
    }
    public ExploreScreenController getExploreScreenController() {
        return exploreScreenController;
    }
    public BattleController getBattleController() {
        return battleController;
    }
    public SwitchScreenController getSwitchScreenController() {
        return switchScreenController;
    }
}
