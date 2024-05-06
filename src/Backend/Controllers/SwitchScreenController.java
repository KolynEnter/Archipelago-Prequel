package Backend.Controllers;

import Backend.BattleSystem.CardSystem.CardDealer;
import Backend.BattleSystem.EnemyEncounter;
import Backend.Characters.Enemies.Wolf;
import Backend.Characters.GameObject;
import Backend.Characters.PlayerRelated.Player;
import Backend.GameCore;
import Backend.GameTrackers.BattleTracker;
import Backend.GameTrackers.GameTracker;
import Frontend.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchScreenController {

    private final UI ui;
    private final SwitchScreenHandler switchSceneHandler;
    private final EnemyEncounter enemyEncounter = new EnemyEncounter();

    public SwitchScreenController(GameCore core) {
        this.ui = core.getUI();
        switchSceneHandler = new SwitchScreenHandler(core.getGameTracker());
    }

    public class SwitchScreenHandler implements ActionListener {

        private final GameTracker gameTracker;

        public SwitchScreenHandler(GameTracker gameTracker) {
            this.gameTracker = gameTracker;
        }

        public void actionPerformed(ActionEvent e) {
            BattleTracker battleTracker = gameTracker.getBattleTracker();

            // Game screen is in ArchiveScreenController
            switch(e.getActionCommand()) {
                case "EnterMainScreen":
                    ui.getArchiveScreen().exitArchiveScreen();
                    ui.getMainScreen().showMainScreen();
                    System.out.println("Enter the main screen");
                    break;
                case "EnterArchiveScreen":
                    ui.getMainScreen().exitMainScreen();
                    ui.getArchiveScreen().showArchiveScreen();
                    System.out.println("Enter the archive screen.");
                    break;
                case "Save0":
                case "Save1":
                case "Save2":
                    int saveNumber = Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().length()-1));
                    System.out.println("In Save " + saveNumber);
                    if (ui.getArchiveScreen().getInUseSaveSlots()[saveNumber]) { // start game with this save
                        ui.getArchiveScreen().exitArchiveScreen();
                        ui.getExploreScreen().showGameScreen(ui);
                    } else { // create a new one based on this
                        // Before overwrite, set up the game settings
                        ui.getArchiveScreen().assignCreateNewSaveScreen(saveNumber);
                    }
                    break;
                case "EnterBattleScreen":
                    battleTracker.assignEnemies(enemyEncounter.getEnemies());
                    battleTracker.assignAllies(new GameObject[]{new Wolf(true), new Player(), new Wolf(true)});

                    ui.getExploreScreen().exitGameScreen(ui);
                    ui.getBattleScreen().showBattleScreen(battleTracker.getMobsOnField());
                    CardDealer dealer = new CardDealer(ui.getBattleScreen().getCardsPanelManager());
                    dealer.giveStartingCards();

                    System.out.println("Enter the battle screen.");
                    break;

                case "EnterResultScreen":

                    System.out.println("Enter the battle result screen.");
                    break;
                default:
                    System.out.println("SWITCH SCREEN HANDLER ERROR.");
                    break;
            }
        }
    }

    public SwitchScreenHandler getSwitchScreenHandler() {
        return switchSceneHandler;
    }
}
