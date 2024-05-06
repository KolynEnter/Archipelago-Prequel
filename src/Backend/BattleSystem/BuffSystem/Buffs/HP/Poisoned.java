package Backend.BattleSystem.BuffSystem.Buffs.HP;

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.BattleSystem.HpManager;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

/*
Reduce target's 10% Current HP for one turn
 */

public class Poisoned extends BuffObject {

    public Poisoned(GameObject applier) {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 7));
        super.getSustainabilityManager().setSustainability(new boolean[]{false, false}, 1);
        super.setBuffIcon("Poisoned.png");
        super.setTriggerCode(BuffType.BEFORE_TURN.ordinal());
        super.setApplier(applier);
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {
        new HpManager().reduceByCurrentHealthPointRatio(new GameObject[]{carrier}, getApplier(), 0.1f, recordDisplayManager);
        super.getSustainabilityManager().stackDecrement();
    }
}
