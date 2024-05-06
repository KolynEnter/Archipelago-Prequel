package Backend.BattleSystem.CardSystem.Cards;

import Backend.BattleSystem.CardSystem.TargetSelectionManager;
import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

import javax.swing.*;

public class CardObject {

    private String cardName;
    private final TargetSelectionManager targetSelectionManager = new TargetSelectionManager();
    private float attackRatio;
    private String cardType;
    private ImageIcon cardSprite;
    private int cardCost;

    public void activate(GameObject user, GameObject[] targets, RecordDisplayManager recordDisplayManager) {
        String message = Lang.adaptLineToMessage(new Object[] {user, getCardName()},
                LangMan.getContent(LangFile.RecordDisplayManager, 2));
        recordDisplayManager.addTextToBattleRecord(message);
    }

    public GameObject[] getDefinedTargets(GameObject user, GameObject[] mobsOnField) {
        return null;
    }

    public String toString() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public String getCardName() {
        return cardName;
    }
    public TargetSelectionManager getTargetSelectionManager() {
        return targetSelectionManager;
    }
    public float getAttackRatio() {
        return attackRatio;
    }
    public void setAttackRatio(float attackRatio) {
        this.attackRatio = attackRatio;
    }
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public void addSpecificationToCardSprite(String specification) {
        String path = "lib/Images/Cards/" + specification;
        this.cardSprite = new ImageIcon(path);
    }
    public ImageIcon getCardSprite() {
        return cardSprite;
    }
    public int getCardCost() {
        return cardCost;
    }
    public void setCardCost(int cardCost) {
        this.cardCost = cardCost;
    }
}
