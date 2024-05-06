package Backend.Controllers;

import Backend.ArchiveSystem.InUseSaveSlotsRewriter;
import Backend.LoadSave.SaveOverwriter;
import Backend.ArchiveSystem.GameSettingStorage;
import Frontend.Screens.ArchiveScreen;
import Frontend.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArchiveScreenController {

    private final UI ui;
    private final ArchiveScreenHandler accountScreenHandler = new ArchiveScreenHandler();
    private final String[] gameModes = new String[] {"RogueLike", "StoryMode"};
    private int currentGameMode = 0;
    private final InUseSaveSlotsRewriter rewriter = new InUseSaveSlotsRewriter();

    public ArchiveScreenController(UI ui) {
        this.ui = ui;
    }

    public class ArchiveScreenHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "DeleteAnExistingSave": // delete button
                    System.out.println("Delete an existing save");
                    // Use SaveOverwriter to delete things
                    break;
                case "StartOneNewSave": // complete button
                    ArchiveScreen archiveScreen = ui.getArchiveScreen();
                    System.out.println("Start a new save");
                    GameSettingStorage storage = archiveScreen.storeGameSetting();
                    storage.setGameMode(currentGameMode);
                    // Write to InUseSaveSlots.txt
                    rewriter.write(archiveScreen.getNewSaveIndex());
                    createOneNewSave(archiveScreen.getNewSaveIndex(), storage);

                    // Back to save selection
                    archiveScreen.exitArchiveScreen();
                    archiveScreen.showArchiveScreen();
                    break;
                case "BackToSaveSelection":
                    System.out.println("Back to save selection");
                    archiveScreen = ui.getArchiveScreen();
                    archiveScreen.exitArchiveScreen();
                    archiveScreen.showArchiveScreen();
                    break;
                case "GameModeSwitch":
                    archiveScreen = ui.getArchiveScreen();
                    System.out.println("Switch game mode");
                    if (currentGameMode == gameModes.length-1) {
                        currentGameMode = 0;
                    } else {
                        currentGameMode++;
                    }
                    archiveScreen.getModeButton().setText("-> "+gameModes[currentGameMode]);
                    archiveScreen.getModeButton().revalidate();
                    archiveScreen.getModeButton().repaint();
                    break;
                default:
                    System.out.println("ARCHIVE SCREEN CONTROLLER ERROR.");
                    break;
            }
        }
    }

    // Overwrite save slot 01/02/03 with DoNotChange save slot
    public void createOneNewSave(int overwritedSlot, GameSettingStorage storage) {
        SaveOverwriter saveOverwriter = new SaveOverwriter(overwritedSlot, storage); // needs to know game difficulty
        saveOverwriter.overwrite();
    }

    public ArchiveScreenHandler getArchiveScreenHandler() {
        return accountScreenHandler;
    }
}
