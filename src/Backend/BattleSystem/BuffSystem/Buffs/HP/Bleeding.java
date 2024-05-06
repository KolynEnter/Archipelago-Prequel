package Backend.BattleSystem.BuffSystem.Buffs.HP;

/*
Reduce target's 5% current HP for two turns
 */

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.BattleSystem.HpManager;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

public class Bleeding extends BuffObject {

    public Bleeding(GameObject applier) {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 5));
        super.getSustainabilityManager().setSustainability(new boolean[]{true, false}, 2);
        super.setBuffIcon("Bleeding.png");
        super.setTriggerCode(BuffType.AFTER_TURN.ordinal());
        super.setApplier(applier);
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {
        new HpManager().reduceByCurrentHealthPointRatio(new GameObject[]{carrier}, getApplier(),0.05f, recordDisplayManager);
        super.getSustainabilityManager().stackDecrement();
    }
}
