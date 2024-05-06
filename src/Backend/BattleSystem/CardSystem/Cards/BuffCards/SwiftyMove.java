package Backend.BattleSystem.CardSystem.Cards.BuffCards;

import Backend.BattleSystem.BuffSystem.BuffManager;
import Backend.BattleSystem.BuffSystem.Buffs.Speed.Swift;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;


public class SwiftyMove extends CardObject {

    public SwiftyMove() {
        super.setCardName(LangMan.getContent(LangFile.CardName, 5));
        super.getTargetSelectionManager().setSpecification(false, 0, 1);
        super.setAttackRatio(0);
        super.setCardType("Buff");
        super.addSpecificationToCardSprite("Swifty_Move.png");
        super.setCardCost(2);
    }

    public void activate(GameObject user, GameObject[] targets, RecordDisplayManager recordDisplayManager) {
        super.activate(user, targets, recordDisplayManager);
        String message = Lang.adaptLineToMessage(new Object[] {user},
                LangMan.getContent(LangFile.CardMessage, 3));
        recordDisplayManager.addTextToBattleRecord(message);
        BuffManager buffManager = new BuffManager();
        GameObject[] definedTargets = getDefinedTargets(user, targets);
        for (GameObject target :definedTargets) {
            buffManager.addBuff(target, user, new Swift(), recordDisplayManager);
        }
    }

    public GameObject[] getDefinedTargets(GameObject user, GameObject[] mobsOnField) {
        return new GameObject[] {user};
    }
}
