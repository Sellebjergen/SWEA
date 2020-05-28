package hotciv.broker.main.Broker.Stubs;

import hotciv.broker.main.Broker.BrokerConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

import java.util.UUID;

public class StubUnit2 implements Unit {
  private String type;
  private Player owner;
  private int attStrength;
  private int defStrength;
  private int move;
  private String id;

  public StubUnit2(String type, Player owner) {
    this.id = UUID.randomUUID().toString();

    this.type = type;
    this.owner = owner;

    attStrength = 2;
    defStrength = 2;
    move = 1;
  }

  @Override
  public String getTypeString() {
    return type;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public int getMoveCount() {
    return move;
  }

  @Override
  public int getDefensiveStrength() {
    return defStrength;
  }

  @Override
  public int getAttackingStrength() {
    return attStrength;
  }

  @Override
  public void archerEffect(int defence, int moveCount) {
    move = 0;
    defStrength = 4;
  }

  public String getId() {
    return id;
  }
}