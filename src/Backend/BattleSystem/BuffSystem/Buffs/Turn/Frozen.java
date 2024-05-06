package Backend.BattleSystem.BuffSystem.Buffs.Turn;

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;


public class Frozen extends BuffObject {

    public Frozen() {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 10));
        super.getSustainabilityManager().setSustainability(new boolean[]{false, false}, 1);
        super.setBuffIcon("Frozen.png");
        super.setTriggerCode(BuffType.BEFORE_TURN.ordinal());
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {
        // make the carrier lose its turn

    }
}
