package Backend.BattleSystem.BuffSystem.Buffs.Attack;

import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.BattleSystem.DamageManager;
import Backend.BattleSystem.HpManager;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

/*
Before receive damage, ignore this damage and return
carrier's raw attack - attacker's raw attack damage to
the attacker
 */

public class SwordDefense extends BuffObject {

    public SwordDefense(GameObject applier) {
        super.setBuffName(LangMan.getContent(LangFile.BuffName, 11));
        super.getSustainabilityManager().setSustainability(new boolean[]{false, false}, 1);
        super.setBuffIcon("Sword_Defense.png");
        super.setTriggerCode(BuffType.BEFORE_DAMAGED.ordinal());
        super.setEvadable(false);
        super.setApplier(applier);
    }

    public void activate(GameObject attacker, RecordDisplayManager recordDisplayManager) {
        String message = Lang.adaptLineToMessage(new Object[]{getApplier(), attacker}, LangMan.getContent(LangFile.BuffMessage, 0));
        recordDisplayManager.addTextToBattleRecord(message);

        float returnDamage = getApplier().getAttribute().getAttackDefault() - attacker.getAttribute().getAttackDefault();
        if (returnDamage < 0) {
            returnDamage = 0;
        }
        HpManager hpManager = new HpManager();
        hpManager.reduceByFixedDamage(getApplier(), new GameObject[]{attacker}, returnDamage, null, recordDisplayManager);
        message = Lang.adaptLineToMessage(new Object[]{attacker, returnDamage, getBuffName()}, LangMan.getContent(LangFile.BuffMessage, 1));
        recordDisplayManager.addTextToBattleRecord(message);
        getSustainabilityManager().stackDecrement();
    }
}
