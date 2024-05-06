package Backend.GameTrackers;

import Frontend.UI;

public class GameTracker {

    private final BattleTracker battleTracker;

    public GameTracker(UI ui) {
        battleTracker = new BattleTracker(ui);
    }

    public BattleTracker getBattleTracker() {
        return battleTracker;
    }
}
