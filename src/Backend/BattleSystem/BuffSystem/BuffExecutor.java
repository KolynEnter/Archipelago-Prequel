package Backend.BattleSystem.BuffSystem;

import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

public class BuffExecutor {

    private final BuffManager buffManager = new BuffManager();

    public BuffExecutor() {

    }

    public void executeBuff(RecordDisplayManager recordDisplayManager, GameObject target, BuffType buffType) {
        if (target == null) {
            return;
        }

        for (int i = 0; i < target.getBuffList().size(); i++) {
            BuffObject buff = target.getBuffList().get(i);
            if (buff.getTriggerCode() == buffType.ordinal()) {
                String message = Lang.adaptLineToMessage(new Object[]{target, buff}, LangMan.getContent(LangFile.RecordDisplayManager, 3));
                recordDisplayManager.addTextToBattleRecord(message);
                buff.activate(target, recordDisplayManager);
                if (buff.getSustainabilityManager().getStack() <= 0) {
                    buffManager.removeBuff(target, i);
                }
            }
        }
    }

    public void executeBeforeDamagedBuff(RecordDisplayManager recordDisplayManager, GameObject target, GameObject attacker) {
        if (target == null) {
            return;
        }

        for (int i = 0; i < target.getBuffList().size(); i++) {
            BuffObject buff = target.getBuffList().get(i);
            if (buff.getTriggerCode() == BuffType.BEFORE_DAMAGED.ordinal()) {
                String message = Lang.adaptLineToMessage(new Object[]{target, buff}, LangMan.getContent(LangFile.RecordDisplayManager, 3));
                recordDisplayManager.addTextToBattleRecord(message);
                buff.activate(attacker, recordDisplayManager);
                if (buff.getSustainabilityManager().getStack() <= 0) {
                    buffManager.removeBuff(target, i);
                }
            }
        }
    }
}
