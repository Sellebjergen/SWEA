package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.UUID;

public class CityImpl implements hotciv.framework.City {
  private Player owner;
  private int treasury;
  private String workFocus;
  private String producing;
  private int foodStorage;
  private int citySize;
  private int foodRate;
  private int productionRate;
  private String id;

  public CityImpl(Player owner, String workFocus, String producing) {
    this.owner = owner;
    this.workFocus = workFocus;
    this.producing = producing;

    treasury = 0;
    foodStorage = 0;
    citySize = 1;

    id = UUID.randomUUID().toString();
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  public void changeOwnership(Player newOwner) {
    owner = newOwner;
  }

  @Override
  public int getSize() {
    return citySize;
  }

  @Override
  public int getTreasury() {
    return treasury;
  }

  public int getFoodStorage() {
    return foodStorage;
  }

  @Override
  public String getProduction() {
    return producing;
  }

  @Override
  public String getWorkforceFocus() {
    return workFocus;
  }

  public void incrementTreasury() {
    treasury += productionRate;
  }

  public void incrementFoodStorage() {
    foodStorage += foodRate;
  }

  public void newFoodRate(int newFoodRate) {
    foodRate = newFoodRate;
  }

  public void newProductionRate(int newProductionRate) {
    productionRate = newProductionRate;
  }

  public void changeProduction(String newUnitProduction) {
    producing = newUnitProduction;
  }

  public void changeWorkFocus(String newWorkFocus) {
    workFocus = newWorkFocus;
  }

  public void incrementCitySize() {
    foodStorage = 0;
    citySize += 1;
  }

  public boolean canAffordUnit() {
    int producingCost = GameConstants.UNIT_COST_MAP.get(getProduction());
    return getTreasury() >= producingCost;
  }

  public void buyProductionUnit() {
    treasury -= GameConstants.UNIT_COST_MAP.get(getProduction());
  }

  public void decreasePopulation() {
    citySize -= 1;
  }

  @Override
  public String getId() {
    return id;
  }
}
