package Backend.ArchiveSystem;

import Backend.FileManagers.MyInputManager;
import Backend.FileManagers.MyOutputManager;


// Read the file, record it
// Add the new index to appropriate position.
// Write them into the file

public class InUseSaveSlotsRewriter {

    private String[] inUseSaveSlotsIndicators = new String[3];

    public InUseSaveSlotsRewriter() {
        inUseSaveSlotsIndicators[0] = null;
        inUseSaveSlotsIndicators[1] = null;
        inUseSaveSlotsIndicators[2] = null;
    }

    public void write(int newSaveIndex) {
        assignIndicators();
        addNewIndex(newSaveIndex);
        writeToInUseSaveSlotsFile();
    }

    private void assignIndicators() {
        MyInputManager inputManager = new MyInputManager("lib/LoadSave/InUseSaveSlots.txt");
        inUseSaveSlotsIndicators = inputManager.readFileAsStringArray(inUseSaveSlotsIndicators.length);
        inputManager.closeBoth();
    }

    private void addNewIndex(int newSaveIndex) {
        inUseSaveSlotsIndicators[newSaveIndex] = newSaveIndex + "";
    }

    private void writeToInUseSaveSlotsFile() {
        String newContent = assignNewFileContent();
        MyOutputManager outputManager = new MyOutputManager("lib/LoadSave/InUseSaveSlots.txt");
        outputManager.writeOneLine(newContent.toString());
        outputManager.closeWriter();
    }

    private String assignNewFileContent() {
        StringBuilder newContent = new StringBuilder();
        for (int i = 0; i < inUseSaveSlotsIndicators.length-1; i++) {
            if (inUseSaveSlotsIndicators[i] != null) {
                newContent.append(inUseSaveSlotsIndicators[i]);
            }
            newContent.append("\n");
        }
        if (inUseSaveSlotsIndicators[inUseSaveSlotsIndicators.length - 1] != null) {
            newContent.append(inUseSaveSlotsIndicators[inUseSaveSlotsIndicators.length - 1]);
        }

        return newContent.toString();
    }
}
