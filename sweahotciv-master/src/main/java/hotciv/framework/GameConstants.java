package hotciv.framework;

import java.util.HashMap;
import java.util.Map;

public class GameConstants {
  // The size of the world is set permanently to a 16x16 grid 
  public static final int WORLDSIZE = 16;

  // Valid unit types
  public static final String ARCHER    = "archer";
  public static final String LEGION    = "legion";
  public static final String SETTLER   = "settler";
  public static final String B52       = "b52";

  // Valid terrain types
  public static final String PLAINS    = "plains";
  public static final String OCEANS    = "ocean";
  public static final String FOREST    = "forest";
  public static final String HILLS     = "hills";
  public static final String MOUNTAINS = "mountain";

  // Valid production balance types
  public static final String productionFocus = "hammer";
  public static final String foodFocus = "apple";

  // amount of production each round
  public static final Integer productionRate = 6;
  public static final Integer startTreasury = 0;

  // unit movement
  public static final Integer landMoveCount = 1;
  public static final Integer flyingMoveCount = 2;

  // unit attack and defense strength
  public static final Map<String, Integer> UNIT_ATTACK_MAP = new HashMap<String, Integer>(){{
    put(ARCHER, 2);
    put(LEGION, 4);
    put(SETTLER, 0);
    put(B52, 1);
  }};
  public static final Map<String, Integer> UNIT_DEFEND_MAP = new HashMap<String, Integer>(){{
    put(ARCHER, 3);
    put(LEGION, 2);
    put(SETTLER, 3);
    put(B52, 8);
  }};
  public static final Map<String, Integer> UNIT_MOVECOUNT_MAP = new HashMap<String, Integer>(){{
    put(ARCHER, landMoveCount);
    put(LEGION, landMoveCount);
    put(SETTLER, landMoveCount);
    put(B52, flyingMoveCount);
  }};
  public static final Map<String, Integer> UNIT_COST_MAP = new HashMap<String, Integer>(){{
    put(ARCHER, 10);
    put(LEGION, 15);
    put(SETTLER, 30);
    put(B52, 60);
  }};

  // unit cost
  public static final Integer archerCost = 10;
  public static final Integer legionCost = 15;
  public static final Integer settlerCost = 30;
  public static final Integer b52Cost = 60;
}
