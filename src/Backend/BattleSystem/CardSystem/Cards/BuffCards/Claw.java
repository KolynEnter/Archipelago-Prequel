package Backend.BattleSystem.CardSystem.Cards.BuffCards;

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.Buffs.HP.Bleeding;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.BattleSystem.HpManager;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;


public class Claw extends CardObject {

    public Claw() {
        super.setCardName(LangMan.getContent(LangFile.CardName, 0));
        super.getTargetSelectionManager().setSpecification(true, 1, 0);
        super.setAttackRatio(0.7f);
        super.setCardType("Buff");
        super.addSpecificationToCardSprite("Claw.png");
        super.setCardCost(1);
    }

    public void activate(GameObject user, GameObject[] targets, RecordDisplayManager recordDisplayManager) {
        super.activate(user, targets, recordDisplayManager);
        new HpManager().reduceByUserAttackRatio(user,
                targets,
                getAttackRatio(),
                new BuffObject[]{new Bleeding(user)},
                recordDisplayManager);
    }
}
