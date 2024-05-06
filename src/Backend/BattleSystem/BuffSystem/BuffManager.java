package Backend.BattleSystem.BuffSystem;

import Backend.BattleSystem.ChanceCalculator;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.BuffDisplayer;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

import java.util.ArrayList;

public class BuffManager {

    private final BuffDisplayer buffDisplayer = new BuffDisplayer();
    private final ChanceCalculator chanceCalculator = new ChanceCalculator();

    public BuffManager() {

    }

    public void addBuff(GameObject victim, GameObject attacker, BuffObject buff, RecordDisplayManager recordDisplayManager) {
        /*
        Check if buff is refreshable and existing in the list already
          - if yes, refresh the buff
        - if not, add the buff to list
         */
        if (!chanceCalculator.applyIsSuccessful(attacker, victim) && buff.isEvadable()) {
            String message = Lang.adaptLineToMessage(new Object[] {victim, buff}, LangMan.getContent(LangFile.RecordDisplayManager, 9));
            recordDisplayManager.addTextToBattleRecord(message);
            return;
        }

        if (buffExistInTheList(victim, buff)) {
            if (!buff.getSustainabilityManager().isRefreshable() && ! buff.getSustainabilityManager().isStackable()) {
                return;
            }
            for (BuffObject buffObject : victim.getBuffList()) {
                if (buffObject.getBuffName().equals(buff.getBuffName())) {
                    buff.getSustainabilityManager().refreshBuff();
                    break;
                }
            }
        } else {
            victim.addBuff(recordDisplayManager, buff);
            buffDisplayer.addBuffIconToPanel(victim.getBuffPanel(), buff);
        }
    }

    private boolean buffExistInTheList(GameObject mob, BuffObject buff) {
        for (BuffObject buffObject : mob.getBuffList()) {
            if (buffObject.getBuffName().equals(buff.getBuffName())) {
                return true;
            }
        }
        return false;
    }

    public void removeBuff(GameObject mob, int indexInList) {
        /*
        Activate the buff, if its sustainability is 0
        Remove the buff from list
         */
        ArrayList<BuffObject> buffList = mob.getBuffList();
        int indexInPanel = buffList.get(indexInList).getIndexInPanel();
        buffList.remove(indexInList);
        buffDisplayer.removeBuffIconInPanel(mob, indexInPanel);
    }

    public boolean targetHasBeforeDamagedBuff(GameObject target) {
        for (BuffObject buff : target.getBuffList()) {
            if (buff.getTriggerCode() == BuffType.BEFORE_DAMAGED.ordinal()) {
                return true;
            }
        }
        return false;
    }
}
