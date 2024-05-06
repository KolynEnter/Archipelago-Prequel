package Backend.Characters;

public class CharacterAttribute {

    private float attackDefault;
    private float healthMax;
    private float healthCurrent;
    private float defenseDefault;
    private float hitDefault;
    private float evadeDefault;
    private float speedDefault;
    private float resistanceDefault;
    private float luckDefault;
    private float critDefault;

    public void setAttribute(float[] defaults) {
        attackDefault = defaults[0];
        healthMax = defaults[1];
        healthCurrent = healthMax;
        defenseDefault = defaults[2];
        hitDefault = defaults[3];
        evadeDefault = defaults[4];
        speedDefault = defaults[5];
        resistanceDefault = defaults[6];
        luckDefault = defaults[7];
        critDefault = defaults[8];
    }

    public float getAttackDefault() {
        return attackDefault;
    }
    public float getHealthMax() {
        return healthMax;
    }
    public float getHealthCurrent() {
        return healthCurrent;
    }
    public float getDefenseDefault() {
        return defenseDefault;
    }
    public float getHitDefault() {
        return hitDefault;
    }
    public float getEvadeDefault() {
        return evadeDefault;
    }
    public float getSpeedDefault() {
        return speedDefault;
    }
    public float getResistanceDefault() {
        return resistanceDefault;
    }
    public float getLuckDefault() {
        return luckDefault;
    }
    public float getCritDefault() {
        return critDefault;
    }

    public void setHealthCurrent(float healthCurrent) {
        this.healthCurrent = healthCurrent;
    }
}
