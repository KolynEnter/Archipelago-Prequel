package Backend.BattleSystem.BuffSystem.Buffs.HP;

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;


public class Burning extends BuffObject {

    public Burning() {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 6));
        super.getSustainabilityManager().setSustainability(new boolean[]{false, false}, 1);
        super.setBuffIcon("Burning.png");
        super.setTriggerCode(BuffType.BEFORE_TURN.ordinal());
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {

    }
}
