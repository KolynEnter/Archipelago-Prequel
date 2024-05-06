package Backend.LoadSave;

import Backend.ArchiveSystem.GameSettingStorage;
import Backend.FileManagers.MyOutputManager;

import java.io.*;
import java.nio.channels.FileChannel;

public class SaveOverwriter {

    private final String overwritedSavePackage;
    private final String doNotChangeSavePackage;
    private final GameSettingStorage storage;

    public SaveOverwriter(int overwritedSlot, GameSettingStorage storage) {
        overwritedSavePackage = "lib/LoadSave/SaveSlot0" + overwritedSlot;
        doNotChangeSavePackage = "lib/LoadSave/DoNotChangeSaveSlot";
        this.storage = storage;
    }

    public void overwrite() {
        String[] files = new String[] {
                "/Achievements/CompletedAchievements.txt",
                "/Bestiary/Bestiary.txt",
                "/CardInDeck/CardInDeck.txt",
                "/CardOwned/CardOwned.txt",
                // "/SaveSetting/SaveSetting.txt", overwrite by storage
                "/Items/Items.txt",
                "/Notes/Notes.txt",
                "/Player/CurrentTask.txt",
                "/Player/PlayerCurrentHP.txt",
                "/Player/PlayerLevel.txt",
                "/Player/PlayerName.txt",
                "/Player/PlayerStates.txt",
                "/UnitHired/UnitHired.txt",
                "/UnitInUse/UnitInUse.txt",
                "/World/CurrentMap.txt",
                "/World/CurrentWeather.txt",
                "/World/PLayerPositionOnMap.txt"
        };

        for (String file : files) {
            File source = new File(doNotChangeSavePackage + file);
            File dest = new File(overwritedSavePackage + file);
            try {
                copyFile(source, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        overwriteSaveSetting();
    }

    private void overwriteSaveSetting() {
        MyOutputManager outputManager = new MyOutputManager(overwritedSavePackage + "/SaveSetting/SaveSetting.txt");
        outputManager.writeOneLine(storage.inputForFile());
        outputManager.closeWriter();
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }
}
