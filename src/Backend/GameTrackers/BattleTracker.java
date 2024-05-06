package Backend.GameTrackers;

import Backend.BattleSystem.Bonuser;
import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.BattleSystem.Turn.SpeedBarTracker;
import Backend.BattleSystem.Turn.TurnTracker;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.BattleScreen;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Frontend.UI;

import java.util.ArrayList;
import java.util.Arrays;

public class BattleTracker {

    private final TurnTracker turnTracker = new TurnTracker(this);
    private final SpeedBarTracker speedBarTracker = new SpeedBarTracker(turnTracker);
    private int cardIndex = 0;
    private final GameObject[] mobsOnField = new GameObject[6];
    private CardObject lastUsedCard = null;
    private boolean isSomeCardClicked = false;
    private final String[] targetSelectionList = new String[4];
    private final ArrayList<BuffObject>[] buffsOnField = new ArrayList[6];
    private final Bonuser[] bonusers = new Bonuser[6];
    private int currentPlayerMana = 1;
    private final UI ui;

    public BattleTracker(UI ui) {
        this.ui = ui;
        resetMobsOnField();
    }

    public void assignEnemies(GameObject[] enemies) {
        assignMobsBasedOnIndex(0, enemies[0]);
        assignMobsBasedOnIndex(1, enemies[1]);
        assignMobsBasedOnIndex(2, enemies[2]);
    }

    public void assignAllies(GameObject[] allies) {
        assignMobsBasedOnIndex(3, allies[0]);
        assignMobsBasedOnIndex(4, allies[1]);
        assignMobsBasedOnIndex(5, allies[2]);
    }

    private void assignMobsBasedOnIndex(int index, GameObject mob) {
        if (mob == null) {
            return;
        }
        mobsOnField[index] = mob;
        buffsOnField[index] = new ArrayList<>();
        bonusers[index] = new Bonuser();
        mob.setBuffList(buffsOnField[index]);
        mob.setIndexInField(index);
        mob.setBonuser(bonusers[index]);
    }

    public void resetMobsOnField() {
        Arrays.fill(mobsOnField, null);
    }

    private GameObject[] getSelectedMobs() {
        int len = 0;
        for (String target : targetSelectionList) {
            if (target != null) {
                len++;
            }
        }
        GameObject[] selectedMobs = new GameObject[len];
        int count = 0;
        for (String target : targetSelectionList) {
            if (target != null) {
                int index = Integer.parseInt(target.substring(target.length()-1));
                selectedMobs[count] = mobsOnField[index];
                count++;
            }
        }

        return selectedMobs;
    }

    public void playerActivateLastUsedCard(RecordDisplayManager recordDisplayManager) {
        lastUsedCard.activate(mobsOnField[4], getSelectedMobs(), recordDisplayManager);
        currentPlayerMana -= lastUsedCard.getCardCost();
    }

    public void resetCardClickAndSelection() {
        isSomeCardClicked = false;
        Arrays.fill(targetSelectionList, null);
    }

    public void manaIncrement() {
        if (currentPlayerMana >= 10) {
            return;
        }
        currentPlayerMana++;
    }

    public CardObject getLastUsedCard() {
        return lastUsedCard;
    }
    public void setLastUsedCard(CardObject lastUsedCard) {
        this.lastUsedCard = lastUsedCard;
    }
    public boolean isSomeCardClicked() {
        return isSomeCardClicked;
    }
    public void setSomeCardClicked(boolean someCardClicked) {
        isSomeCardClicked = someCardClicked;
    }
    public String[] getTargetSelectionList() {
        return targetSelectionList;
    }
    public GameObject[] getMobsOnField() {
        return mobsOnField;
    }
    public int getCardIndex() {
        return cardIndex;
    }
    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }
    public ArrayList<BuffObject>[] getBuffsOnField() {
        return buffsOnField;
    }
    public Bonuser[] getBonusers() {
        return bonusers;
    }
    public TurnTracker getTurnTracker() {
        return turnTracker;
    }
    public SpeedBarTracker getSpeedBarTracker() {
        return speedBarTracker;
    }
    public int getCurrentPlayerMana() {
        return currentPlayerMana;
    }
    public void setCurrentPlayerMana(int currentPlayerMana) {
        this.currentPlayerMana = currentPlayerMana;
    }
    public BattleScreen getBattleScreen() {
        return ui.getBattleScreen();
    }
}
