package Backend.Characters.PlayerRelated;

import Backend.BattleSystem.BuffSystem.BuffExecutor;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.BattleSystem.CardSystem.CardDealer;
import Backend.BattleSystem.Turn.SpeedBarTracker;
import Backend.Characters.GameObject;
import Backend.Characters.Gender;
import Backend.GameTrackers.BattleTracker;
import Frontend.Screens.BattleScreenPack.BattleScreen;
import Frontend.Screens.BattleScreenPack.PanelManagers.MultiPurposePanelManager;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Frontend.UI;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

public class Player extends GameObject {

    public Player() {
        super.setName("Joshua");
        super.getAttribute().setAttribute(new float[] {100, 100, 0, 100, 0, 60, 0, 0, 0});
        super.setLevel(1);
        super.addSpecificationToSpriteSheet("Joshua.png");
        super.setGender(Gender.MALE);
    }

    public void turnEnds(int position, BattleTracker battleTracker) {
        BattleScreen battleScreen = battleTracker.getBattleScreen();
        MultiPurposePanelManager multiPurposePanelManager = battleScreen.getMultiPurposePanelManager();
        RecordDisplayManager recordDisplayManager = multiPurposePanelManager.getMultiPurposeDisplayVM().getRecordDisplayManager();

        if (!battleTracker.getTurnTracker().isPlayerTurnStarted()) {
            String message = Lang.adaptLineToMessage(new Object[] {}, LangMan.getContent(LangFile.RecordDisplayManager, 6));
            recordDisplayManager.addTextToBattleRecord(message);
            return;
        }

        recordDisplayManager.reshowBattleRecordScrollPane();
        multiPurposePanelManager.resetSelection(battleTracker);

        CardDealer cardDealer = new CardDealer(battleScreen.getCardsPanelManager());
        cardDealer.deal();

        BuffExecutor buffExecutor = new BuffExecutor();
        buffExecutor.executeBuff(recordDisplayManager, battleTracker.getMobsOnField()[4], BuffType.AFTER_TURN);

        battleTracker.getTurnTracker().setPlayerTurnStarted(false);
        battleTracker.manaIncrement();
        battleScreen.getManaDisplayer().updateCurrentMana(battleTracker.getCurrentPlayerMana());

        SpeedBarTracker speedBarTracker = battleTracker.getSpeedBarTracker();
        speedBarTracker.increaseMovementValue(recordDisplayManager, battleTracker.getMobsOnField());

        battleTracker.getMobsOnField()[4].setCannotMove(false);
    }
}
