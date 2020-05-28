package hotciv.broker.main.Broker;


public class BrokerConstants {
  public final static String gameID = "game";
  public final static String cityID = "city";
  public final static String unitID = "unit";
  public final static String tileID = "tile";
  public final static int status_OK = 200;

  // for game
  public final static String endOfTurn_operation = "_endOfTurn";
  public final static String getWinner_operation = "_getWinner";
  public final static String getPlayerInTurn_operation = "_getPlayerInTurn";
  public final static String getAge_operation = "_getAge";
  public final static String moveUnit_operation = "_moveUnit";
  public final static String changeWorkFocus_operation = "_changeWorkForceFocus";
  public final static String changeProduction_operation = "_changeProduction";
  public final static String performUnitAction_operation = "_performUnitAction";

  // for city
  public final static String getCityOwner_operation = "_getOwner";
  public final static String getSize_operation = "_getSize";
  public final static String getTreasury_operation = "_getTreasury";
  public final static String getFoodStorage_operation = "_getFoodStorage";
  public final static String getProduction_operation = "_getProduction";
  public final static String getWorkforceFocus_operation = "_getWorkforceFocus";
  public final static String getCity_operation = "_getCity";

  // for unit
  public final static String getUnitTypeString_operation = "_getTypeString";
  public final static String getUnitOwner_operation = "_getOwner";
  public final static String getMoveCount_operation = "_getMoveCount";
  public final static String getDefensiveStrength_operation = "_getDefensiveStrength";
  public final static String getAttackingStrength_operation = "_getAttackingStrength";
  public final static String archerEffect_operation = "_archerEffect";
  public final static String getUnit_operation = "_getUnit";

  // for tile
  public final static String getTileTypeString_operation = "_getTypeString";
  public final static String getTile_operation = "_getTile";

  // for server
  public final static int serverPort = 37123;
}
