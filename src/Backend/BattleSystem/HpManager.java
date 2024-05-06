package Backend.BattleSystem;

import Backend.BattleSystem.BuffSystem.BuffExecutor;
import Backend.BattleSystem.BuffSystem.BuffManager;
import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.BattleSystem.BuffSystem.BuffType;
import Backend.BattleSystem.BuffSystem.Buffs.Turn.Dead;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

import java.text.DecimalFormat;

public class HpManager {

    private final ChanceCalculator chanceCalculator = new ChanceCalculator();
    private final DamageManager damageManager = new DamageManager();

    public HpManager() {

    }

    public void reduceByCurrentHealthPointRatio(GameObject[] targets, GameObject user,
                                                float currentHpRatio,
                                                RecordDisplayManager recordDisplayManager) {
        for (GameObject target : targets) {
            float currentHP = target.getAttribute().getHealthCurrent();
            float damage = currentHP * currentHpRatio;
            float newHP = getValidHP(currentHP - damage);
            reduceHealth(target, user, newHP, recordDisplayManager);
            String message = Lang.adaptLineToMessage(new Object[] {target, damage}, LangMan.getContent(LangFile.RecordDisplayManager, 1));
            recordDisplayManager.addTextToBattleRecord(message);
        }
    }

    // calculate damage, hit, crit as a whole
    // calculate damage taken, evade, defense, luck, res individually.
    public void reduceByUserAttackRatio(GameObject user,
                                        GameObject[] targets,
                                        float attackRatio,
                                        BuffObject[] possibleBuffs,
                                        RecordDisplayManager recordDisplayManager) {
        // This is a before-attack situation
        // check if the user has before-attack buff and active it
        BuffExecutor executor = new BuffExecutor();
        executor.executeBuff(recordDisplayManager, user, BuffType.BEFORE_ATTACK);
        float userDamage = damageManager.calculateUserDamage(recordDisplayManager, user, attackRatio);

        BuffManager buffManager = new BuffManager();
        for (GameObject target : targets) {
            if (!chanceCalculator.attackIsSuccessful(user, target)) {
                String message = Lang.adaptLineToMessage(new Object[] {user, target}, LangMan.getContent(LangFile.RecordDisplayManager, 8));
                recordDisplayManager.addTextToBattleRecord(message);
                continue;
            }

            // check for victim's before_damaged buffs
            if (buffManager.targetHasBeforeDamagedBuff(target)) {
                executor.executeBeforeDamagedBuff(recordDisplayManager, target, user);
                continue;
            }

            float currentHP = target.getAttribute().getHealthCurrent();
            float actualDamage = damageManager.calculateActualDamage(userDamage, target);
            float newHP = getValidHP(currentHP - actualDamage);
            reduceHealth(target, user, newHP, recordDisplayManager);

            if (possibleBuffs != null) {
                for (BuffObject buffObject : possibleBuffs) {
                    buffManager.addBuff(target, user, buffObject, recordDisplayManager);
                }
            }
            String message = Lang.adaptLineToMessage(new Object[] {target, actualDamage}, LangMan.getContent(LangFile.RecordDisplayManager, 1));
            recordDisplayManager.addTextToBattleRecord(message);
        }
    }

    public void reduceByFixedDamage(GameObject user,
                                        GameObject[] targets,
                                        float damage,
                                        BuffObject[] possibleBuffs,
                                        RecordDisplayManager recordDisplayManager) {
        BuffManager buffManager = new BuffManager();
        for (GameObject target : targets) {
            float currentHP = target.getAttribute().getHealthCurrent();
            float newHP = getValidHP(currentHP - damage);
            reduceHealth(target, user, newHP, recordDisplayManager);

            if (possibleBuffs != null) {
                for (BuffObject buffObject : possibleBuffs) {
                    buffManager.addBuff(target, user, buffObject, recordDisplayManager);
                }
            }
            String message = Lang.adaptLineToMessage(new Object[] {target, damage}, LangMan.getContent(LangFile.RecordDisplayManager, 1));
            recordDisplayManager.addTextToBattleRecord(message);
        }
    }

    private void reduceHealth(GameObject target, GameObject user, float newHP, RecordDisplayManager recordDisplayManager) {
        target.getAttribute().setHealthCurrent(newHP);
        float percent = new HpManager().calculateHealthPointPercent(target);
        target.getHpBar().setValue((int) percent);
        target.getHpBar().setString("HP: " + new DecimalFormat("0.0").format(percent) + "%");

        addDeadBuffIfDead(target, user, recordDisplayManager);
    }

    public float calculateHealthPointPercent(GameObject gameObject) {
        return gameObject.getAttribute().getHealthCurrent() / gameObject.getAttribute().getHealthMax() * 100;
    }

    private float getValidHP(float source) {
        if (source >= 0.001) {
            return source;
        } else {
            return 0;
        }
    }

    private void addDeadBuffIfDead(GameObject target, GameObject user, RecordDisplayManager recordDisplayManager) {
        if (target.getAttribute().getHealthCurrent() < 0.001) {
            target.setDead(true);
            new BuffManager().addBuff(target, user, new Dead(), recordDisplayManager);
        }
    }
}
