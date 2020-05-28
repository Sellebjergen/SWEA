package hotciv.broker.main.Broker.Stubs;

import hotciv.broker.main.Broker.BrokerConstants;
import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.UUID;

public class StubCity5 implements City {
  private Player owner;
  private int size;
  private String producing;
  private String workFocus;
  private String id;

  public StubCity5(Player owner, int size) {
    this.id = UUID.randomUUID().toString();

    this.owner = owner;
    this.size = size;

    producing = GameConstants.ARCHER;
    this.workFocus = GameConstants.foodFocus;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public int getTreasury() {
    return 12;
  }

  @Override
  public int getFoodStorage() {
    return 6;
  }

  @Override
  public String getProduction() {
    return producing;
  }

  @Override
  public String getWorkforceFocus() {
    return workFocus;
  }

  public void changeProduction(String newUnitProduction) {
    producing = newUnitProduction;
  }

  public void changeWorkFocus(String newWorkFocus) {
    workFocus = newWorkFocus;
  }

  public String getId() {
    return id;
  }
}