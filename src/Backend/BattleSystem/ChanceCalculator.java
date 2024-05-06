package Backend.BattleSystem;

import Backend.Characters.CharacterAttribute;
import Backend.Characters.GameObject;

import java.util.Random;

public class ChanceCalculator {

    public ChanceCalculator() {

    }

    public boolean attackIsSuccessful(GameObject attacker, GameObject victim) {
        CharacterAttribute attackerAttribute = attacker.getAttribute();
        CharacterAttribute victimAttribute = victim.getAttribute();
        float victimDefaultEvade = victimAttribute.getEvadeDefault();
        float victimDefaultSpeed = victimAttribute.getSpeedDefault();
        float victimDefaultLuck = victimAttribute.getLuckDefault();
        float attackerDefaultHit = attackerAttribute.getHitDefault();
        float attackerDefaultSpeed = attackerAttribute.getSpeedDefault();
        float attackerDefaultLuck = attackerAttribute.getLuckDefault();

        Bonuser victimBonuser = victim.getBonuser();
        Bonuser attackerBonuser = attacker.getBonuser();
        float victimActualEvade = victimBonuser.applyBonus("Evade", victimDefaultEvade);
        float victimActualSpeed = victimBonuser.applyBonus("Speed", victimDefaultSpeed);
        float victimActualLuck = victimBonuser.applyBonus("Luck", victimDefaultLuck);
        float attackerActualHit = attackerBonuser.applyBonus("Hit", attackerDefaultHit);
        float attackerActualSpeed = attackerBonuser.applyBonus("Speed", attackerDefaultSpeed);
        float attackerActualLuck = attackerBonuser.applyBonus("Luck", attackerDefaultLuck);

        float evadeRange = (
                victimActualEvade/100 +
                    victimActualSpeed/100 +
                    victimActualLuck/10) -
                (attackerActualHit/100 +
                    attackerActualSpeed/100 +
                    attackerActualLuck/10
                );

        float hitRange = 1 - evadeRange;
        if (hitRange > 1) {
            hitRange = 1;
        }
        if (hitRange < 0) {
            hitRange = 0;
        }

        Random rand = new Random();
        float cursor = rand.nextFloat();

        return cursor < hitRange;
    }

    public boolean applyIsSuccessful(GameObject attacker, GameObject victim) {
        CharacterAttribute attackerAttribute = attacker.getAttribute();
        CharacterAttribute victimAttribute = victim.getAttribute();
        float victimDefaultRes = victimAttribute.getResistanceDefault();
        float victimDefaultLuck = victimAttribute.getLuckDefault();
        float attackerDefaultHit = attackerAttribute.getHitDefault();
        float attackerDefaultLuck = attackerAttribute.getLuckDefault();

        Bonuser victimBonuser = victim.getBonuser();
        Bonuser attackerBonuser = attacker.getBonuser();
        float victimActualRes = victimBonuser.applyBonus("Evade", victimDefaultRes);
        float victimActualLuck = victimBonuser.applyBonus("Luck", victimDefaultLuck);
        float attackerActualHit = attackerBonuser.applyBonus("Hit", attackerDefaultHit);
        float attackerActualLuck = attackerBonuser.applyBonus("Luck", attackerDefaultLuck);

        float evadeRange = (
                victimActualRes/100 +
                        victimActualLuck/10) -
                (attackerActualHit/100 +
                        attackerActualLuck/10
                );
        float hitRange = 1 - evadeRange;
        if (hitRange > 1) {
            hitRange = 1;
        }
        if (hitRange < 0) {
            hitRange = 0;
        }

        Random rand = new Random();
        float cursor = rand.nextFloat();
        return cursor < hitRange;
    }
}
