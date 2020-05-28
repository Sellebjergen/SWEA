package hotciv.standard;

import hotciv.framework.*;

import hotciv.framework.GameConstants;

import java.util.UUID;

public class UnitImpl implements Unit {
  private String type;
  private Player owner;
  private boolean isGroundUnit;
  private int defence;
  private int attack;
  private int moveCount = GameConstants.landMoveCount;
  private String id;

  public UnitImpl(String type, Player owner) {
    this.type = type;
    this.owner = owner;
    resetStrength(type);
    determineFlyingOrGround();
    id = UUID.randomUUID().toString();
  }

  public String getTypeString() {
    return type;
  }

  public Player getOwner() {
    return owner;
  }

  public int getMoveCount() {
    return moveCount;
  }

  public void decreaseMoveCount() {
    this.moveCount -= 1;
  }

  public int getDefensiveStrength() {
    return defence;
  }

  public int getAttackingStrength() {
    return attack;
  }

  public void archerEffect(int defence, int moveCount) {
    this.defence = defence;
    this.moveCount = moveCount;
  }

  // ===== Helping methods ===== //
  private void resetStrength(String type) {
    this.attack = GameConstants.UNIT_ATTACK_MAP.get(type);
    this.defence = GameConstants.UNIT_DEFEND_MAP.get(type);
  }

  private void determineFlyingOrGround() {
    isGroundUnit = !type.equals(GameConstants.B52);
  }

  boolean getIsGroundUnit() {
    return isGroundUnit;
  }

  void resetMoveCount() {
    this.moveCount = GameConstants.UNIT_MOVECOUNT_MAP.get(type);
  }

  @Override
  public String getId() {
    return id;
  }
}
