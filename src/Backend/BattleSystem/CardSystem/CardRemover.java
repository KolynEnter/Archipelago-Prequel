package Backend.BattleSystem.CardSystem;

import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Frontend.Screens.BattleScreenPack.PanelManagers.CardsPanelManager;

public class CardRemover {

    private final CardObject[] handCardTracker;

    public CardRemover(CardObject[] handCardTracker) {
        this.handCardTracker = handCardTracker;
    }

    public void remove(int index, CardsPanelManager cardsPanelManager) {
        handCardTracker[index] = null;
        cardsPanelManager.refreshCardsPanel();
    }
}
