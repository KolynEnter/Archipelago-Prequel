package Backend.BattleSystem.CardSystem.Cards.DamageCards;

import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.BattleSystem.HpManager;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;


public class Bite extends CardObject {

    public Bite() {
        super.setCardName(LangMan.getContent(LangFile.CardName, 6));
        super.getTargetSelectionManager().setSpecification(true, 1, 0);
        super.setAttackRatio(1);
        super.setCardType("Damage");
        super.addSpecificationToCardSprite("Bite.png");
        super.setCardCost(1);
    }

    public void activate(GameObject user, GameObject[] targets, RecordDisplayManager recordDisplayManager) {
        super.activate(user, targets, recordDisplayManager);
        new HpManager().reduceByUserAttackRatio(user,
                targets,
                getAttackRatio(),
                null,
                recordDisplayManager);
    }
}
