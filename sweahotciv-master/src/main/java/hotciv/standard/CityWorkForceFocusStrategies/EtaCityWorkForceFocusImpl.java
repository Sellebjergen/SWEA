package hotciv.standard.CityWorkForceFocusStrategies;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.CityWorkForceFocus;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;

import java.util.HashMap;

public class EtaCityWorkForceFocusImpl implements CityWorkForceFocus {
  private int numberOfPlains;
  private int numberOfOcenas;
  private int numberOfForrests;
  private int numberOfMountains;
  private int numberofHills;

  @Override
  public void changeWorkForceFocusInCityAt(Position p,
                                           String balance,
                                           GameImpl game) {

    game.getCityAt(p).changeWorkFocus(balance);
    CityImpl city = game.getCityAt(p);

    resetTileCount();
    countTilesAroundCity(p, game.getTiles());

    if(city.getWorkforceFocus().equals(GameConstants.productionFocus)) {
      game.getCityAt(p).newFoodRate(1);
      game.getCityAt(p).newProductionRate(getProductionRate(city.getSize(), city.getWorkforceFocus()));
    }
    else if(city.getWorkforceFocus().equals(GameConstants.foodFocus)) {
      game.getCityAt(p).newProductionRate(1);
      game.getCityAt(p).newFoodRate(getFoodRate(city.getSize(), city.getWorkforceFocus()));
    }
  }

  @Override
  public int getProductionRate(int citySize, String workFocus) {
    if(! workFocus.equals(GameConstants.productionFocus)) {
      return 1;
    }

    return calculateTilesToWork(citySize, workFocus);
  }

  @Override
  public int getFoodRate(int citySize, String workFocus) {
    if(! workFocus.equals(GameConstants.foodFocus)) {
      return 1;
    }

    return calculateTilesToWork(citySize, workFocus);
  }


  private int calculateTilesToWork(int citySize, String workFocus) {
    int sumOfWorkForce = 1;
    int populationLeft = citySize - 1;

    while (populationLeft > 0) {
      if (workFocus.equals(GameConstants.productionFocus)) {
        if(numberOfForrests > 0) {
          sumOfWorkForce += 3;
          --numberOfForrests;
          --populationLeft;
        }
        else if(numberofHills > 0) {
          sumOfWorkForce += 2;
          --numberofHills;
          --populationLeft;
          }
        else if(numberOfMountains > 0) {
          sumOfWorkForce += 1;
          --numberOfMountains;
          --populationLeft;

        }
        else {
          return sumOfWorkForce;
        }
      }

      else if (workFocus.equals(GameConstants.foodFocus)) {
        if(numberOfPlains > 0) {
          sumOfWorkForce += 3;
          --numberOfPlains;
          --populationLeft;
        }
        else if(numberOfOcenas > 0) {
          sumOfWorkForce += 1;
          --numberOfOcenas;
          --populationLeft;
        }
        else {
          return sumOfWorkForce;
        }
      }
    }
    return sumOfWorkForce;
  }


  private void countTilesAroundCity(Position p, HashMap<Position, TileImpl> tiles) {
    for (Position tile : hotciv.utility.Utility.get8neighborhoodOf(p)) {
      switch (tiles.get(tile).getTypeString()) {
        case GameConstants.PLAINS:
          ++numberOfPlains;
          break;
        case GameConstants.OCEANS:
          ++numberOfOcenas;
          break;
        case GameConstants.FOREST:
          ++numberOfForrests;
          break;
        case GameConstants.MOUNTAINS:
          ++numberOfMountains;
          break;
        case GameConstants.HILLS:
          ++numberofHills;
          break;
      }
    }
  }

  private void resetTileCount() {
    numberOfPlains = 0;
    numberOfOcenas = 0;
    numberOfForrests = 0;
    numberOfMountains = 0;
    numberofHills = 0;
  }
}