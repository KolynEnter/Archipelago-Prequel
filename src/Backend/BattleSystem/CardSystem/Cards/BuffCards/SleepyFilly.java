package Backend.BattleSystem.CardSystem.Cards.BuffCards;

import Backend.BattleSystem.BuffSystem.BuffManager;
import Backend.BattleSystem.BuffSystem.Buffs.Turn.Sleeping;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

public class SleepyFilly extends CardObject {

    public SleepyFilly() {
        super.setCardName(LangMan.getContent(LangFile.CardName, 9));
        super.getTargetSelectionManager().setSpecification(true, 1, 0);
        super.setAttackRatio(0);
        super.setCardType("Buff");
        super.addSpecificationToCardSprite("Sleepy_Filly.png");
        super.setCardCost(1);
    }

    public void activate(GameObject user, GameObject[] targets, RecordDisplayManager recordDisplayManager) {
        super.activate(user, targets, recordDisplayManager);
        BuffManager buffManager = new BuffManager();
        for (GameObject target : targets) {
            buffManager.addBuff(target, user, new Sleeping(), recordDisplayManager);
        }
    }
}
