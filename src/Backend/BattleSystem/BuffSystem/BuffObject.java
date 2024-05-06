package Backend.BattleSystem.BuffSystem;

import Backend.Characters.GameObject;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;

import javax.swing.*;

public class BuffObject {

    private String buffName;
    private final SustainabilityManager sustainabilityManager = new SustainabilityManager();
    private ImageIcon buffIcon;
    private int triggerCode;
    private int indexInPanel;
    private boolean isEvadable = true;
    private GameObject applier;

    public void activate(GameObject carrier, RecordDisplayManager recordDisplayManager) {

    }

    public String toString() {
        return buffName;
    }

    public String getBuffName() {
        return buffName;
    }
    public void setBuffName(String buffName) {
        this.buffName = buffName;
    }
    public SustainabilityManager getSustainabilityManager() {
        return sustainabilityManager;
    }
    public ImageIcon getBuffIcon() {
        return buffIcon;
    }
    public void setBuffIcon(String specification) {
        String path = "lib/Images/Buffs/" + specification;
        this.buffIcon = new ImageIcon(path);
    }
    public int getTriggerCode() {
        return triggerCode;
    }
    public void setTriggerCode(int triggerCode) {
        this.triggerCode = triggerCode;
    }
    public int getIndexInPanel() {
        return indexInPanel;
    }
    public void setIndexInPanel(int indexInPanel) {
        this.indexInPanel = indexInPanel;
    }
    public boolean isEvadable() {
        return isEvadable;
    }
    public void setEvadable(boolean evadable) {
        isEvadable = evadable;
    }
    public GameObject getApplier() {
        return applier;
    }
    public void setApplier(GameObject applier) {
        this.applier = applier;
    }
}
