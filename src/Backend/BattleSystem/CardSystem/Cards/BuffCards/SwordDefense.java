package Backend.BattleSystem.CardSystem.Cards.BuffCards;

import Backend.BattleSystem.BuffSystem.BuffManager;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

public class SwordDefense extends CardObject {

    public SwordDefense() {
        super.setCardName(LangMan.getContent(LangFile.CardName, 8));
        super.getTargetSelectionManager().setSpecification(false, 0, 1);
        super.setAttackRatio(0);
        super.setCardType("Buff");
        super.addSpecificationToCardSprite("Sword_Defense.png");
        super.setCardCost(3);
    }

    public void activate(GameObject user, GameObject[] targets, RecordDisplayManager recordDisplayManager) {
        super.activate(user, targets, recordDisplayManager);
        GameObject[] definedTargets = getDefinedTargets(user, targets);
        BuffManager buffManager = new BuffManager();
        for (GameObject target :definedTargets) {
            buffManager.addBuff(target, user, new Backend.BattleSystem.BuffSystem.Buffs.Attack.SwordDefense(user), recordDisplayManager);
        }
    }

    public GameObject[] getDefinedTargets(GameObject user, GameObject[] mobsOnField) {
        return new GameObject[] {user};
    }
}
