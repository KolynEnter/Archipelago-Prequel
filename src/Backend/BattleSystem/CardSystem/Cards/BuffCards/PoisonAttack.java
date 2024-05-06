package Backend.BattleSystem.CardSystem.Cards.BuffCards;

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.Buffs.HP.Poisoned;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.BattleSystem.HpManager;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;


public class PoisonAttack extends CardObject {

    public PoisonAttack() {
        super.setCardName(LangMan.getContent(LangFile.CardName, 3));
        super.getTargetSelectionManager().setSpecification(true, 1, 0);
        super.setAttackRatio(0.5f);
        super.setCardType("Buff");
        super.addSpecificationToCardSprite("Poison_Attack.png");
        super.setCardCost(2);
    }

    public void activate(GameObject user, GameObject[] targets, RecordDisplayManager recordDisplayManager) {
        super.activate(user, targets, recordDisplayManager);
        new HpManager().reduceByUserAttackRatio(user,
                targets,
                getAttackRatio(),
                new BuffObject[]{new Poisoned(user)},
                recordDisplayManager);
    }
}
