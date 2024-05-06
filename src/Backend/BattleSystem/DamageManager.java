package Backend.BattleSystem;

import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

import java.util.Random;

public class DamageManager {

    public DamageManager() {

    }

    public float calculateUserDamage(RecordDisplayManager recordDisplayManager, GameObject user, float attackRatio) {
        float userDamage = user.getAttribute().getAttackDefault() * attackRatio;
        userDamage = user.getBonuser().applyBonus("Attack", userDamage);

        if (isCrit(recordDisplayManager, user)) {
            userDamage *= 2;
        }

        return userDamage;
    }

    private boolean isCrit(RecordDisplayManager recordDisplayManager, GameObject user) {
        Random rand = new Random();
        int cursor = rand.nextInt(101);

        float userCrit = user.getAttribute().getCritDefault();
        userCrit = user.getBonuser().applyBonus("Crit", userCrit);
        float userLuck = user.getAttribute().getLuckDefault();
        userLuck = user.getBonuser().applyBonus("Luck", userLuck);

        boolean crit = (cursor <= (userCrit + userLuck));

        if (crit) {
            String message = Lang.adaptLineToMessage(new Object[]{user}, LangMan.getContent(LangFile.RecordDisplayManager, 10));
            recordDisplayManager.addTextToBattleRecord(message);
        }

        return crit;
    }

    public float calculateActualDamage(float userDamage, GameObject victim) {
        float victimDefaultDef = victim.getAttribute().getDefenseDefault();
        float victimDef = victim.getBonuser().applyBonus("Defense", victimDefaultDef);

        float damage = userDamage - victimDef;
        if (damage < 0) {
            damage = 0;
        }
        return damage;
    }
}
