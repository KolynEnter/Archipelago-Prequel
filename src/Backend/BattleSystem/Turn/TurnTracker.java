package Backend.BattleSystem.Turn;

import Backend.BattleSystem.BuffSystem.BuffExecutor;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.Characters.GameObject;
import Backend.GameTrackers.BattleTracker;
import Frontend.Screens.BattleScreenPack.BattleScreen;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

import java.util.HashSet;
import java.util.Set;

public class TurnTracker {

    private final BattleTracker battleTracker;
    private boolean playerTurnStarted = true;
    private GameObject currentTurnOwner;
    private int turnLoop = 1; // after all mobs moved, plus 1
    private final Set<Integer> currentMoveSet = new HashSet<>(); // whenever current has all, loop + 1 and reset it

    public TurnTracker(BattleTracker battleTracker) {
        this.battleTracker = battleTracker;
    }

    public void nonPlayerMobGetsTurn(RecordDisplayManager recordDisplayManager) {
        // apply mob AI
        // currentTurnOwner is set at this point
        BuffExecutor buffExecutor = new BuffExecutor();
        buffExecutor.executeBuff(recordDisplayManager, battleTracker.getMobsOnField()[currentTurnOwner.getIndexInField()], BuffType.BEFORE_TURN);
        currentTurnOwner.turnStarts(recordDisplayManager, battleTracker);
    }

    public void playerGetsTurn(RecordDisplayManager recordDisplayManager) {
        // unlock cards
        // when ended, lock cards and turn end
        String message = Lang.adaptLineToMessage(new Object[]{}, LangMan.getContent(LangFile.RecordDisplayManager, 7));
        recordDisplayManager.addTextToBattleRecord(message);
        playerTurnStarted = true;
        BuffExecutor buffExecutor = new BuffExecutor();
        buffExecutor.executeBuff(recordDisplayManager, battleTracker.getMobsOnField()[4], BuffType.BEFORE_TURN);
        if (battleTracker.getMobsOnField()[4].isCannotMove()) {
            battleTracker.getMobsOnField()[4].turnEnds(4, battleTracker);
        }

        currentTurnOwner.turnStarts(recordDisplayManager, battleTracker);
    }

    public void checkIfOneSideHasWon() {
        boolean enemyWon = true;
        boolean playerWon = true;

        for (int i = 0; i <= 2; i++) {
            if (battleTracker.getMobsOnField()[i].getAttribute().getHealthCurrent() >= 0.001) {
                playerWon = true;
            }
        }

        for (int i = 3; i < 6; i++) {
            if (battleTracker.getMobsOnField()[i].getAttribute().getHealthCurrent() >= 0.001) {
                enemyWon = true;
            }
        }
    }

    public void calculateTurnLoop(GameObject[] mobsOnField) {
        currentMoveSet.add(currentTurnOwner.getIndexInField());
        Set<Integer> fullyMoveSet = new HashSet<>();
        for (GameObject mob : mobsOnField) {
            if (mob == null) {
                continue;
            }
            fullyMoveSet.add(mob.getIndexInField());
        }

        if (currentMoveSet.containsAll(fullyMoveSet)) {
            currentMoveSet.clear();
            turnLoop++;
        }
    }


    public boolean isPlayerTurnStarted() {
        return playerTurnStarted;
    }
    public void setPlayerTurnStarted(boolean playerTurnStarted) {
        this.playerTurnStarted = playerTurnStarted;
    }
    public GameObject getCurrentTurnOwner() {
        return currentTurnOwner;
    }
    public void setCurrentTurnOwner(GameObject currentTurnOwner) {
        this.currentTurnOwner = currentTurnOwner;
    }
    public int getTurnLoop() {
        return turnLoop;
    }
}
