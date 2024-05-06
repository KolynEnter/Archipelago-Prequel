package Backend.BattleSystem.CardSystem;

public class TargetSelectionManager {

    private boolean requireSpecification;
    private int enemySelection = 0;
    private int allySelection = 0;
    private boolean requireEnemySelection;
    private boolean requireAllySelection;

    public void setSpecification(boolean requireSpecification, int enemySelection, int allySelection) {
        this.requireSpecification = requireSpecification;
        this.enemySelection = enemySelection;
        this.allySelection = allySelection;
        if (enemySelection != 0) {
            requireEnemySelection = true;
        }
        if (allySelection != 0) {
            requireAllySelection = true;
        }
    }

    public String getSelectionText() {
        if (enemySelection != 0 && allySelection != 0) {
            String result = "<html>";
            if (enemySelection > 1) {
                result += "<br>Select ("+ enemySelection +") enemies";
            } else {
                result += "<br>Select ("+ enemySelection +") enemy";
            }
            if (allySelection > 1) {
                result += "<br>Select ("+ allySelection +") allies";
            } else {
                result += "<br>Select ("+ allySelection +") ally";
            }
            return result;
        }
        if (allySelection != 0) {
            if (allySelection > 1) {
                return "<html><br>Select ("+ allySelection +") allies";
            } else {
                return "<html><br>Select ("+ allySelection +") ally";
            }
        }
        if (enemySelection > 1) {
            return "<html><br>Select ("+ enemySelection +") enemies";
        } else {
            return "<html><br>Select ("+ enemySelection +") enemy";
        }
    }

    public int getEnemySelection() {
        return enemySelection;
    }
    public int getAllySelection() {
        return allySelection;
    }
    public boolean isRequireSpecification() {
        return requireSpecification;
    }
    public boolean isRequireEnemySelection() {
        return requireEnemySelection;
    }
    public boolean isRequireAllySelection() {
        return requireAllySelection;
    }
}
