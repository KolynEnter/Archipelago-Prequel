package Backend.BattleSystem.BuffSystem.Buffs.Speed;

import Backend.BattleSystem.Bonuser;
import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.Characters.GameObject;
import Backend.Operation;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

/*
Increase 30% speed for three turns
 */

public class Swift extends BuffObject {

    public Swift() {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 8));
        super.getSustainabilityManager().setSustainability(new boolean[]{true, false}, 3);
        super.setBuffIcon("Swift.png");
        super.setTriggerCode(BuffType.AFTER_TURN.ordinal());
        super.setEvadable(false);
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {
        Bonuser.Magnification magnification = new Bonuser.Magnification();
        magnification.setOperation(Operation.MULTIPLY);
        magnification.setBonus(1.3f);
        carrier.getBonuser().addBonus("Speed", magnification, getSustainabilityManager());
        super.getSustainabilityManager().stackDecrement();
    }
}
