package Backend.Characters;

import Backend.BattleSystem.Bonuser;
import Backend.BattleSystem.BuffSystem.BuffObject;
import Backend.GameTrackers.BattleTracker;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.Lang;
import Language.LangFile;
import Language.LangMan;

import javax.swing.*;
import java.util.ArrayList;

public class GameObject {

    private String name;
    private final CharacterAttribute attribute = new CharacterAttribute();
    private int level;
    private JProgressBar hpBar, movementBar;
    private JPanel buffPanel;
    private ArrayList<BuffObject> buffList;
    private float valueInMovementBar;
    private int indexInField;
    private ImageIcon spriteSheet;
    private boolean cannotMove;
    private boolean isDead;
    private Bonuser bonuser;
    private Gender gender = Gender.THING;

    public void turnStarts(RecordDisplayManager recordDisplayManager, BattleTracker battleTracker) {

    }

    public void turnEnds(int position, BattleTracker battleTracker) {

    }

    public void addBuff(RecordDisplayManager recordDisplayManager, BuffObject buff) {
        if (buffList == null) {
            return;
        }
        String message = Lang.adaptLineToMessage(new Object[] {toString(), buff}, LangMan.getContent(LangFile.RecordDisplayManager, 4));
        recordDisplayManager.addTextToBattleRecord(message);
        buffList.add(buff);
    }

    public String getItsPronunciation() {
        return switch (gender) {
            case MALE -> LangMan.getContent(LangFile.Pronunciation, 0);
            case FEMALE -> LangMan.getContent(LangFile.Pronunciation, 1);
            default -> LangMan.getContent(LangFile.Pronunciation, 2);
        };
    }

    public String toString() {
        return name+indexInField;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public CharacterAttribute getAttribute() {
        return attribute;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public JProgressBar getHpBar() {
        return hpBar;
    }
    public void setHpBar(JProgressBar hpBar) {
        this.hpBar = hpBar;
    }
    public JProgressBar getMovementBar() {
        return movementBar;
    }
    public void setBuffPanel(JPanel buffPanel) {
        this.buffPanel = buffPanel;
    }
    public JPanel getBuffPanel() {
        return buffPanel;
    }
    public void setMovementBar(JProgressBar movementBar) {
        this.movementBar = movementBar;
    }
    public ArrayList<BuffObject> getBuffList() {
        return buffList;
    }
    public void setBuffList(ArrayList<BuffObject> buffList) {
        this.buffList = buffList;
    }
    public float getValueInMovementBar() {
        return valueInMovementBar;
    }
    public void setValueInMovementBar(float valueInMovementBar) {
        this.valueInMovementBar = valueInMovementBar;
    }
    public int getIndexInField() {
        return indexInField;
    }
    public void setIndexInField(int indexInField) {
        this.indexInField = indexInField;
    }
    public ImageIcon getSpriteSheet() {
        return spriteSheet;
    }
    public void addSpecificationToSpriteSheet(String specification) {
        String filePath = "lib/Images/Units/" + specification;
        spriteSheet = new ImageIcon(filePath);
    }
    public boolean isCannotMove() {
        return cannotMove;
    }
    public void setCannotMove(boolean cannotMove) {
        this.cannotMove = cannotMove;
    }
    public Bonuser getBonuser() {
        return bonuser;
    }
    public void setBonuser(Bonuser bonuser) {
        this.bonuser = bonuser;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public boolean isDead() {
        return isDead;
    }
    public void setDead(boolean dead) {
        isDead = dead;
    }
}
