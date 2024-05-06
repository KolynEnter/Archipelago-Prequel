package Backend.BattleSystem.BuffSystem.Buffs.Attack;

/*
The carrier deals 2X Damage the next time it attacks
 */

import Backend.BattleSystem.Bonuser;
import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.Characters.GameObject;
import Backend.Operation;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

public class Polishing extends BuffObject {

    public Polishing() {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 3));
        super.getSustainabilityManager().setSustainability(new boolean[]{false, false}, 1);
        super.setBuffIcon("Polishing.png");
        super.setTriggerCode(BuffType.BEFORE_ATTACK.ordinal());
        super.setEvadable(false);
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {
        Bonuser.Magnification magnification = new Bonuser.Magnification();
        magnification.setOperation(Operation.MULTIPLY);
        magnification.setBonus(2);
        carrier.getBonuser().addBonus("Attack", magnification, getSustainabilityManager());
        super.getSustainabilityManager().stackDecrement();
    }
}
