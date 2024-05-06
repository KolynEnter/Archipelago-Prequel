package Backend.BattleSystem.CardSystem.Cards.DamageCards;

import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;


public class DoubleStrikes extends Bite {

    public DoubleStrikes() {
        super.setCardName(LangMan.getContent(LangFile.CardName, 7));
        super.getTargetSelectionManager().setSpecification(true, 2, 0);
        super.addSpecificationToCardSprite("Double_Strikes.png");
        super.setCardCost(2);
    }

    public void activate(GameObject user, GameObject[] targets, RecordDisplayManager recordDisplayManager) {
        super.activate(user, targets, recordDisplayManager);
    }
}
