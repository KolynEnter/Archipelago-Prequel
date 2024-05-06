package Backend.BattleSystem.BuffSystem;

public class SustainabilityManager {
    private boolean stackable = false;
    private boolean refreshable = false;
    private int stack = 1;
    private int stackMax = 1;

    public void setSustainability(boolean[] settings, int stackMax) {
        stackable = settings[0];
        refreshable = settings[1];
        if (stackable && refreshable) {
            try {
                throw new Exception("A buff cannot be both stackable and refreshable");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (stackable) {
            this.stack = stackMax;
            this.stackMax = stackMax;
        }
    }

    public void stackIncrement() {
        stack++;
    }

    public void stackDecrement() {
        stack--;
    }

    public void refreshBuff() {
        if (!refreshable) {
            return;
        }
        stack = stackMax;
    }

    public boolean isStackable() {
        return stackable;
    }
    public boolean isRefreshable() {
        return refreshable;
    }
    public int getStack() {
        return stack;
    }
    public int getStackMax() {
        return stackMax;
    }
}
