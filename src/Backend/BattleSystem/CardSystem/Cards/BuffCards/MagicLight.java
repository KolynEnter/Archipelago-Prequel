package Backend.BattleSystem.CardSystem.Cards.BuffCards;

import Backend.BattleSystem.BuffSystem.BuffManager;
import Backend.BattleSystem.BuffSystem.Buffs.Hit.Blind;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

import java.util.Arrays;


public class MagicLight extends CardObject {

    public MagicLight() {
        super.setCardName(LangMan.getContent(LangFile.CardName, 1));
        super.getTargetSelectionManager().setSpecification(true, 1, 0);
        super.setAttackRatio(0);
        super.setCardType("Buff");
        super.addSpecificationToCardSprite("Magic_Light.png");
        super.setCardCost(1);
    }

    public void activate(GameObject user, GameObject[] targets, RecordDisplayManager recordDisplayManager) {
        super.activate(user, targets, recordDisplayManager);
        String message = Lang.adaptLineToMessage(new Object[] {Arrays.toString(targets)},
                LangMan.getContent(LangFile.CardMessage, 0));
        recordDisplayManager.addTextToBattleRecord(message);
        BuffManager buffManager = new BuffManager();
        for (GameObject target : targets) {
            buffManager.addBuff(target, user, new Blind(), recordDisplayManager);
        }
    }
}
