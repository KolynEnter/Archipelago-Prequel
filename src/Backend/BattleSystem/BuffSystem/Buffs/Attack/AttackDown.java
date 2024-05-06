package Backend.BattleSystem.BuffSystem.Buffs.Attack;

import Backend.BattleSystem.Bonuser;
import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.Characters.GameObject;
import Backend.Operation;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

/*
Reduce attack by 20% for one turn
 */

public class AttackDown extends BuffObject {

    public AttackDown() {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 1));
        super.getSustainabilityManager().setSustainability(new boolean[]{false, false}, 1);
        super.setBuffIcon("Attack_Down.png");
        super.setTriggerCode(BuffType.BEFORE_ATTACK.ordinal());
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {
        Bonuser.Magnification magnification = new Bonuser.Magnification();
        magnification.setOperation(Operation.MULTIPLY);
        magnification.setBonus(0.8f);
        carrier.getBonuser().addBonus("Attack", magnification, getSustainabilityManager());
        super.getSustainabilityManager().stackDecrement();
    }
}
