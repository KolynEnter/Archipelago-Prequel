package Backend.Controllers.Helpers;

import Backend.BattleSystem.BuffSystem.BuffExecutor;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.BattleSystem.CardSystem.CardDealer;
import Backend.BattleSystem.CardSystem.CardRemover;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.BattleSystem.Turn.SpeedBarTracker;
import Backend.GameTrackers.BattleTracker;
import Frontend.Screens.BattleScreenPack.PanelManagers.MultiPurposePanelManager;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Frontend.UI;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BattleHandlerHelper {

    private final BattleTracker battleTracker;
    private final UI ui;
    private final RecordDisplayManager recordDisplayManager;
    private final MultiPurposePanelManager multiPurposePanelManager;

    public BattleHandlerHelper(BattleTracker battleTracker, UI ui) {
        this.battleTracker = battleTracker;
        this.ui = ui;
        multiPurposePanelManager = ui.getBattleScreen().getMultiPurposePanelManager();
        recordDisplayManager = multiPurposePanelManager.getMultiPurposeDisplayVM().getRecordDisplayManager();
    }

    public void someCard(ActionEvent e) {
        if (!battleTracker.getTurnTracker().isPlayerTurnStarted()) {
            String message = Lang.adaptLineToMessage(new Object[] {}, LangMan.getContent(LangFile.RecordDisplayManager, 6));
            recordDisplayManager.addTextToBattleRecord(message);
            return;
        }
        if (battleTracker.isSomeCardClicked()) { // exit target selection
            recordDisplayManager.reshowBattleRecordScrollPane();
            multiPurposePanelManager.resetSelection(battleTracker);
            return;
        }
        int index = Integer.parseInt(((JButton) e.getSource()).getName().split("Card")[1]);
        CardObject thisCard = ui.getBattleScreen().getCardsPanelManager().getHandCardTracker()[index];
        if (battleTracker.getCurrentPlayerMana() < thisCard.getCardCost()) { // not enough cost
            String message = Lang.adaptLineToMessage(new Object[] {thisCard, thisCard.getCardCost()}, LangMan.getContent(LangFile.RecordDisplayManager, 5));
            recordDisplayManager.addTextToBattleRecord(message);
            return;
        }
        if (thisCard.getTargetSelectionManager().isRequireSpecification()) {
            multiPurposePanelManager.forceToShowTargetSelection(index, battleTracker);
        } else {
            thisCard.activate(battleTracker.getMobsOnField()[4], battleTracker.getMobsOnField(), recordDisplayManager);
            battleTracker.setCurrentPlayerMana(battleTracker.getCurrentPlayerMana() - thisCard.getCardCost());
            ui.getBattleScreen().getManaDisplayer().updateCurrentMana(battleTracker.getCurrentPlayerMana());
            CardRemover cardRemover = new CardRemover(ui.getBattleScreen().getCardsPanelManager().getHandCardTracker());
            cardRemover.remove(index, ui.getBattleScreen().getCardsPanelManager());
        }
    }

    public void someMob(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int index = Integer.parseInt(button.getName().split("Mob")[1]);
        if (!battleTracker.isSomeCardClicked() || battleTracker.getMobsOnField()[index] == null) {
            return;
        }
        multiPurposePanelManager.addMobToTargetSelection((ImageIcon) button.getIcon(), index, battleTracker); // remove if click again
    }

    public void removeMobSelection(ActionEvent e) {
        int index = Integer.parseInt(((JButton) e.getSource()).getName().split("icon")[1]); // search from button in target selection
        multiPurposePanelManager.removeSelection(index, battleTracker);
    }

    public void confirmTargetSelection() {
        // TODO, death of target
        if (!battleTracker.isSomeCardClicked() || !multiPurposePanelManager.isSelectionSatisfied()) {
            return;
        }
        battleTracker.playerActivateLastUsedCard(recordDisplayManager);
        ui.getBattleScreen().getManaDisplayer().updateCurrentMana(battleTracker.getCurrentPlayerMana());
        CardRemover cardRemover = new CardRemover(ui.getBattleScreen().getCardsPanelManager().getHandCardTracker());
        cardRemover.remove(battleTracker.getCardIndex(), ui.getBattleScreen().getCardsPanelManager());
        multiPurposePanelManager.resetSelection(battleTracker);
        recordDisplayManager.reshowBattleRecordScrollPane();
    }

    public void turnEnd() {
        battleTracker.getMobsOnField()[4].turnEnds(4, battleTracker);
    }
}
