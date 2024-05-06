package Backend.Characters;

import Backend.BattleSystem.BuffSystem.BuffExecutor;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.BattleSystem.CardSystem.TargetSelectionManager;
import Backend.GameTrackers.BattleTracker;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;

import java.util.ArrayList;
import java.util.Random;

public class NonPlayerAI {

    private final boolean playerSide;

    public NonPlayerAI(boolean playerSide) {
        this.playerSide = playerSide;
    }

    public void useRandomCard(RecordDisplayManager recordDisplayManager,
                              BattleTracker battleTracker, CardObject[] cardPool, GameObject user) {
        if (user.isCannotMove()) {
            return;
        }

        CardObject randomCard = cardPool[new Random().nextInt(cardPool.length)];
        GameObject[] enemies = getEnemies(battleTracker);
        GameObject[] allies = getAllies(battleTracker);

        if (enemies.length == 0) {
            return;
        }

        TargetSelectionManager tsm = randomCard.getTargetSelectionManager();
        if (tsm.isRequireSpecification()) {
            if (tsm.isRequireEnemySelection()) {
                GameObject[] targets = new GameObject[tsm.getEnemySelection()];

                for (int i = 0; i < tsm.getEnemySelection(); i++) {
                    GameObject target = enemies[new Random().nextInt(enemies.length)];
                    targets[i] = target;
                }
                randomCard.activate(user, targets, recordDisplayManager);
            } else if (tsm.isRequireAllySelection()) {
                GameObject[] targets = new GameObject[tsm.getAllySelection()];
                for (int i = 0; i < tsm.getAllySelection(); i++) {
                    GameObject target = allies[new Random().nextInt(allies.length)];
                    targets[i] = target;
                }
                randomCard.activate(user, targets, recordDisplayManager);
            }
        } else {
            GameObject[] targets = new GameObject[battleTracker.getMobsOnField().length];

            for (int i = 0; i < tsm.getEnemySelection(); i++) {
                GameObject target = enemies[new Random().nextInt(battleTracker.getMobsOnField().length)];
                targets[i] = target;
            }
            randomCard.activate(user, targets, recordDisplayManager);
        }
    }

    private GameObject[] getAllies(BattleTracker battleTracker) {
        GameObject[] enemies = new GameObject[3];
        int count = 0;
        int low = 3;
        int high = 6;
        if (!playerSide) {
            low = 0;
            high = 3;
        }
        for (int i = low; i < high; i++) {
            if (battleTracker.getMobsOnField()[i] != null &&
                    battleTracker.getMobsOnField()[i].getAttribute().getHealthCurrent() > 0.001) {
                enemies[count] = battleTracker.getMobsOnField()[i];
            }
            count++;
        }
        return  getNonNullVersion(enemies);
    }

    private GameObject[] getEnemies(BattleTracker battleTracker) {
        GameObject[] allies = new GameObject[3];
        int count = 0;
        int low = 0;
        int high = 3;
        if (!playerSide) {
            low = 3;
            high = 6;
        }
        for (int i = low; i < high; i++) {
            if (battleTracker.getMobsOnField()[i] != null &&
                    battleTracker.getMobsOnField()[i].getAttribute().getHealthCurrent() > 0.001) {
                allies[count] = battleTracker.getMobsOnField()[i];
            }
            count++;
        }
        return getNonNullVersion(allies);
    }

    public void endTurn(RecordDisplayManager recordDisplayManager, GameObject user, BattleTracker battleTracker) {
        BuffExecutor buffExecutor = new BuffExecutor();
        buffExecutor.executeBuff(recordDisplayManager, user, BuffType.AFTER_TURN);
        user.setCannotMove(false);
        battleTracker.getSpeedBarTracker().increaseMovementValue(recordDisplayManager, battleTracker.getMobsOnField());
    }

    private GameObject[] getNonNullVersion(GameObject[] firstArray) {
        ArrayList<GameObject> list = new ArrayList<GameObject>();

        for(GameObject gameObject : firstArray) {
            if(gameObject != null) {
                list.add(gameObject);
            }
        }

        return list.toArray(new GameObject[list.size()]);
    }
}
