package Backend.Characters.Enemies;

import Backend.BattleSystem.CardSystem.Cards.BuffCards.*;
import Backend.BattleSystem.CardSystem.Cards.CardObject;
import Backend.Characters.GameObject;
import Backend.Characters.NonPlayerAI;
import Backend.GameTrackers.BattleTracker;
import Frontend.Screens.BattleScreenPack.VisibilityManagers.RecordDisplayManager;
import Language.LangFile;
import Language.LangMan;

public class Wolf extends GameObject {

    private final CardObject[] cardPool = new CardObject[] {
            //new Bite(),
            new Claw(),
            new Polish(),
            new MagicLight(),
            //new Nightmare(),
            new SwiftyMove(),
            new SwordDefense(),
            new SleepyFilly()
    };
    private final NonPlayerAI ai;

    public Wolf(boolean playerSide) {
        ai = new NonPlayerAI(playerSide);
        super.setName(LangMan.getContent(LangFile.MobName, 0));
        super.getAttribute().setAttribute(new float[] {10, 100, 0, 70, 0, 50, 0, 7, 0});
        super.setLevel(1);
        super.addSpecificationToSpriteSheet("Wolf.png");
    }

    public void turnStarts(RecordDisplayManager recordDisplayManager, BattleTracker battleTracker) {
        ai.useRandomCard(recordDisplayManager, battleTracker, cardPool, this);
        turnEnds(recordDisplayManager, battleTracker);
    }

    private void turnEnds(RecordDisplayManager recordDisplayManager, BattleTracker battleTracker) {
        ai.endTurn(recordDisplayManager, this, battleTracker);
    }
}
