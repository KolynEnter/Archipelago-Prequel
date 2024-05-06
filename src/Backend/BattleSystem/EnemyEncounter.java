package Backend.BattleSystem;

import Backend.Characters.Enemies.Wolf;
import Backend.Characters.GameObject;

public class EnemyEncounter {

    public EnemyEncounter() {

    }

    public GameObject[] getEnemies() {
        return new GameObject[] {new Wolf(false), new Wolf(false), new Wolf(false)};
    }
}
