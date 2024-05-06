package Backend.BattleSystem.BuffSystem.Buffs.Turn;

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

public class Dead extends BuffObject {

    public Dead() {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 9));
        super.getSustainabilityManager().setSustainability(new boolean[]{false, false}, 1);
        super.setBuffIcon("Dead.png");
        super.setTriggerCode(BuffType.BEFORE_TURN.ordinal());
    }

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {
        getSustainabilityManager().stackIncrement();
        carrier.setCannotMove(true);
        carrier.setDead(true);
    }
}
