package Backend.BattleSystem.BuffSystem.Buffs.Hit;

import Backend.BattleSystem.Bonuser;
import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.Characters.GameObject;
import Backend.Operation;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

public class Blind extends BuffObject {

    public Blind() {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 4));
        super.getSustainabilityManager().setSustainability(new boolean[]{false, false}, 1);
        super.setBuffIcon("Blind.png");
        super.setTriggerCode(BuffType.BEFORE_TURN.ordinal());
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {
        Bonuser.Magnification magnification = new Bonuser.Magnification();
        magnification.setOperation(Operation.MULTIPLY);
        magnification.setBonus(0);
        carrier.getBonuser().addBonus("Hit", magnification, getSustainabilityManager());
        super.getSustainabilityManager().stackDecrement();
    }
}
