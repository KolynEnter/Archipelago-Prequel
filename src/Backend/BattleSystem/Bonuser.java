package Backend.BattleSystem;

/*
Apply bonus effects in this class,
called before the target heal, attack, receive damage, move...
 */

import Backend.BattleSystem.BuffSystem.SustainabilityManager;
import Backend.Operation;

import java.util.HashMap;
import java.util.Map;

public class Bonuser {
    
    private final Map<Magnification, Integer> attackMap = new HashMap<>();
    private final Map<Magnification, Integer> healMap = new HashMap<>();
    private final Map<Magnification, Integer> defenseMap = new HashMap<>();
    private final Map<Magnification, Integer> hitMap = new HashMap<>();
    private final Map<Magnification, Integer> evadeMap = new HashMap<>();
    private final Map<Magnification, Integer> speedMap = new HashMap<>();
    private final Map<Magnification, Integer> resistanceMap = new HashMap<>();
    private final Map<Magnification, Integer> luckMap = new HashMap<>();
    private final Map<Magnification, Integer> critMap = new HashMap<>();
    
    public Bonuser() {
        
    }

    public void addBonus(String type, Magnification magnification,
                         SustainabilityManager sustainabilityManager) {
        Map<Magnification, Integer> definedMap = getDefinedMap(type);
        if (definedMap.containsKey(magnification)) {
            addExistingBonus(definedMap, magnification, sustainabilityManager);
        } else {
            definedMap.put(magnification, sustainabilityManager.getStack());
        }
    }

    private void addExistingBonus(Map<Magnification, Integer> definedMap,
                                  Magnification magnification,
                                  SustainabilityManager sustainabilityManager) {
        if (!sustainabilityManager.isStackable()) {
            return;
        }
        int newStack = definedMap.get(magnification) + sustainabilityManager.getStack();
        if (newStack > sustainabilityManager.getStackMax()) {
            newStack = sustainabilityManager.getStackMax();
        }
        definedMap.put(magnification, newStack);
    }
    
    public float applyBonus(String type, float source) {
        Map<Magnification, Integer> definedMap = getDefinedMap(type);
        float result = source;

        for (Magnification magnification : definedMap.keySet()) {
            switch (magnification.operation) {
                case ADD -> result += magnification.bonus;
                case SUBTRACT -> result -= magnification.bonus;
                case MULTIPLY -> result *= magnification.bonus;
                case DIVIDE -> result /= magnification.bonus;
            }

            int durability = definedMap.get(magnification) - 1;
            if (durability == 0) {
                definedMap.remove(magnification);
            } else {
                definedMap.put(magnification, durability);
            }
        }
        return result;
    }
    
    private Map<Magnification, Integer> getDefinedMap(String type) {
        return switch (type) {
            case "Attack" -> attackMap;
            case "Heal" -> healMap;
            case "Defense" -> defenseMap;
            case "Hit" -> hitMap;
            case "Evade" -> evadeMap;
            case "Speed" -> speedMap;
            case "Resistance" -> resistanceMap;
            case "Luck" -> luckMap;
            case "Crit" -> critMap;
            default -> null;
        };
    }

    public static class Magnification {
        private Operation operation;
        private float bonus;

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof Magnification anotherMag) {
                return this.operation == anotherMag.operation && this.bonus - anotherMag.bonus <= 0.001;
            }
            return false;
        }

        public int hashCode() {
            return -1 * operation.hashCode() + (int) bonus;
        }

        public String toString() {
            return operation.toString() + ": " + bonus;
        }
        public void setOperation(Operation operation) {
            this.operation = operation;
        }
        public void setBonus(float bonus) {
            this.bonus = bonus;
        }
    }
}
