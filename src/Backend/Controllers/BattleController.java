package Backend.Controllers;

import Backend.Controllers.Helpers.BattleHandlerHelper;
import Backend.GameCore;
import Backend.GameTrackers.BattleTracker;
import Frontend.Screens.BattleScreenPack.PanelManagers.MultiPurposePanelManager;
import Frontend.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleController {

    private final BattleTracker battleTracker;
    private final BattleScreenHandler battleScreenHandler;

    public BattleController(GameCore core) {
        UI ui = core.getUI();
        this.battleTracker = core.getGameTracker().getBattleTracker();
        battleScreenHandler = new BattleScreenHandler(ui);
    }

    public class BattleScreenHandler implements ActionListener {

        private final UI ui;

        public BattleScreenHandler(UI ui) {
            this.ui = ui;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MultiPurposePanelManager multiPurposePanelManager = ui.getBattleScreen().getMultiPurposePanelManager();
            BattleHandlerHelper battleHandlerHelper = new BattleHandlerHelper(battleTracker, ui);
            switch (e.getActionCommand()) {
                case "Info":
                    multiPurposePanelManager.changeArrowLocation(0);
                    multiPurposePanelManager.getMultiPurposeDisplayVM().showInfoDisplay();
                    break;
                case "Record":
                    multiPurposePanelManager.changeArrowLocation(1);
                    multiPurposePanelManager.getMultiPurposeDisplayVM().showRecordDisplay();
                    break;
                case "Bag":
                    multiPurposePanelManager.changeArrowLocation(2);
                    multiPurposePanelManager.getMultiPurposeDisplayVM().showBagDisplay();
                    break;
                case "Settings":
                    multiPurposePanelManager.changeArrowLocation(3);
                    multiPurposePanelManager.getMultiPurposeDisplayVM().showSettingsDisplay();
                    break;
                case "Some Card":
                    battleHandlerHelper.someCard(e);
                    break;
                case "Some Mob":
                    battleHandlerHelper.someMob(e);
                    break;
                case "RemoveMobSelection":
                    battleHandlerHelper.removeMobSelection(e);
                    break;
                case "ConfirmTargetSelection":
                    battleHandlerHelper.confirmTargetSelection();
                    break;
                case "Turn End":
                    battleHandlerHelper.turnEnd();
                    break;
                default:
                    System.out.println("Battle Controller Error.");
                    break;
            }
        }
    }

    public BattleScreenHandler getBattleScreenHandler() {
        return battleScreenHandler;
    }
}
