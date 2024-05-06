package Frontend.Screens.BattleScreenPack.VisibilityManagers;

import Backend.Controllers.BattleController;

import javax.swing.*;

public class MultiPurposeDisplayVM {

    private final InfoDisplayManager infoDisplayManager;
    private final RecordDisplayManager recordDisplayManager;
    private final BagDisplayManager bagDisplayManager;
    private final SettingsDisplayManager settingsDisplayManager;

    public MultiPurposeDisplayVM(BattleController.BattleScreenHandler battleScreenHandler, JPanel multiPurposeDisplayPanel, int panelHeight) {
        infoDisplayManager = new InfoDisplayManager(multiPurposeDisplayPanel, panelHeight);
        recordDisplayManager = new RecordDisplayManager(battleScreenHandler, multiPurposeDisplayPanel, panelHeight);
        bagDisplayManager = new BagDisplayManager(multiPurposeDisplayPanel, panelHeight);
        settingsDisplayManager = new SettingsDisplayManager(multiPurposeDisplayPanel, panelHeight);
    }

    public void showInfoDisplay() {
        infoDisplayManager.showInfoDisplay();
        infoDisplayManager.getInfoDisplay().setVisible(true);
        recordDisplayManager.getRecordDisplay().setVisible(false);
        bagDisplayManager.getBagDisplay().setVisible(false);
        settingsDisplayManager.getSettingsDisplay().setVisible(false);
    }

    public void showRecordDisplay() {
        recordDisplayManager.showRecordDisplay();
        infoDisplayManager.getInfoDisplay().setVisible(false);
        recordDisplayManager.getRecordDisplay().setVisible(true);
        bagDisplayManager.getBagDisplay().setVisible(false);
        settingsDisplayManager.getSettingsDisplay().setVisible(false);
    }

    public void showBagDisplay() {
        bagDisplayManager.showBagDisplay();
        infoDisplayManager.getInfoDisplay().setVisible(false);
        recordDisplayManager.getRecordDisplay().setVisible(false);
        bagDisplayManager.getBagDisplay().setVisible(true);
        settingsDisplayManager.getSettingsDisplay().setVisible(false);
    }

    public void showSettingsDisplay() {
        settingsDisplayManager.showSettingsDisplay();
        infoDisplayManager.getInfoDisplay().setVisible(false);
        recordDisplayManager.getRecordDisplay().setVisible(false);
        bagDisplayManager.getBagDisplay().setVisible(false);
        settingsDisplayManager.getSettingsDisplay().setVisible(true);
    }

    public void removeAllChildren() {
        infoDisplayManager.getInfoDisplay().removeAll();
        recordDisplayManager.getRecordDisplay().removeAll();
        bagDisplayManager.getBagDisplay().removeAll();
        settingsDisplayManager.getSettingsDisplay().removeAll();
    }

    public InfoDisplayManager getInfoDisplayManager() {
        return infoDisplayManager;
    }
    public RecordDisplayManager getRecordDisplayManager() {
        return recordDisplayManager;
    }
    public BagDisplayManager getBagDisplayManager() {
        return bagDisplayManager;
    }
    public SettingsDisplayManager getSettingsDisplayManager() {
        return settingsDisplayManager;
    }
}
