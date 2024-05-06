package Backend.BattleSystem.Turn;

import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.BattleScreen;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class SpeedBarTracker {

    private final TurnTracker turnTracker;

    public SpeedBarTracker(TurnTracker turnTracker) {
        this.turnTracker = turnTracker;
    }

    public void increaseMovementValue(RecordDisplayManager recordDisplayManager, GameObject[] mobsOnField) {
        float closestMoveValue = Float.MAX_VALUE;
        GameObject closestMob = null;
        float[] bonusedSpeed = new float[mobsOnField.length];
        Arrays.fill(bonusedSpeed, 0);
        for (int i = 0; i < mobsOnField.length; i++) {
            if (mobsOnField[i] == null || mobsOnField[i].isCannotMove() || mobsOnField[i].isDead()) {
                continue;
            }
            float mobSpeed = mobsOnField[i].getBonuser().applyBonus("Speed", mobsOnField[i].getAttribute().getSpeedDefault());
            float moveValue = (100 - mobsOnField[i].getValueInMovementBar()) / mobSpeed;
            if (moveValue < closestMoveValue) {
                closestMoveValue = moveValue;
                closestMob = mobsOnField[i];
            }

            bonusedSpeed[i] = mobSpeed;
        }

        for (int i = 0; i < mobsOnField.length; i++) {
            GameObject mob = mobsOnField[i];

            if (mob == null || mob.isCannotMove() || mob.isDead()) {
                continue;
            }

            float valueInMovementBar = mob.getValueInMovementBar() + closestMoveValue * bonusedSpeed[i];

            Timer timer = new Timer(10, new ActionListener() {
                private int counter = (int) mob.getValueInMovementBar();

                @Override
                public void actionPerformed(ActionEvent e) {
                    mob.getMovementBar().setValue(counter);
                    counter++;
                    if (counter > valueInMovementBar) {
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            timer.start();
            mob.setValueInMovementBar(valueInMovementBar);
        }

        assert closestMob != null;
        closestMob.setValueInMovementBar(0);
        closestMob.getMovementBar().setValue(0);
        closestMob.getMovementBar().revalidate();
        closestMob.getMovementBar().repaint();

        turnTracker.setCurrentTurnOwner(closestMob);

        if (closestMob.getIndexInField() == 4) {
            turnTracker.playerGetsTurn(recordDisplayManager);
        } else {
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    turnTracker.nonPlayerMobGetsTurn(recordDisplayManager);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }

        turnTracker.calculateTurnLoop(mobsOnField);
    }
}
