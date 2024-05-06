package Backend.BattleSystem.CardSystem;

import Backend.BattleSystem.CardSystem.Cards.BuffCards.Claw;
import Backend.BattleSystem.CardSystem.Cards.BuffCards.Polish;
import Backend.BattleSystem.CardSystem.Cards.BuffCards.SwordDefense;
import Backend.BattleSystem.CardSystem.Cards.DamageCards.DoubleStrikes;
import Backend.BattleSystem.CardSystem.Cards.BuffCards.PoisonAttack;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Frontend.Screens.BattleScreenPack.PanelManagers.CardsPanelManager;

import java.util.Random;

public class CardDealer {

    // Start of each turn, provides hand cards from the card pool
    private final CardObject[] pool = new CardObject[] {
            //new Claw(),
            new Polish(),
            //new PoisonAttack(),
            new DoubleStrikes(),
            //new SwordDefense()
    }; // need to know what the player carries
    private final CardObject[] handCardTracker;
    private final CardsPanelManager cardsPanelManager;

    public CardDealer(CardsPanelManager cardsPanelManager) {
        this.handCardTracker = cardsPanelManager.getHandCardTracker();
        this.cardsPanelManager = cardsPanelManager;
    }

    public void deal() {
        CardObject randomCard = pool[new Random().nextInt(pool.length)];
        addCardToFrontMostSlot(randomCard);
        cardsPanelManager.refreshCardsPanel();
    }

    public void giveStartingCards() {
        for (int i = 0; i < 3; i++){
            deal();
        }
    }

    private void addCardToFrontMostSlot(CardObject card) {
        for (int i = 0; i < handCardTracker.length; i++) {
            if (handCardTracker[i] == null) {
                handCardTracker[i] = card;
                break;
            }
        }
    }
}
