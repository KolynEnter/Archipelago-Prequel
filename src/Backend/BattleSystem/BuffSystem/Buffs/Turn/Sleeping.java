package Backend.BattleSystem.BuffSystem.Buffs.Turn;

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

public class Sleeping extends BuffObject {

    public Sleeping() {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 12));
        super.getSustainabilityManager().setSustainability(new boolean[]{true, false}, 1);
        super.setBuffIcon("Sleeping.png");
        super.setTriggerCode(BuffType.BEFORE_TURN.ordinal());
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {
        carrier.setCannotMove(true);
        super.getSustainabilityManager().stackDecrement();
    }
}
