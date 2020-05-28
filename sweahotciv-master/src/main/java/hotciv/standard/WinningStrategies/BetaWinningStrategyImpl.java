package hotciv.standard.WinningStrategies;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.WinningStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;

import java.util.HashMap;
import java.util.Map;

public class BetaWinningStrategyImpl implements WinningStrategy {
  private int amountOfRedCities = 0;
  private int amountOfBlueCities = 0;

  @Override
  public Player getWinner(GameImpl game) {
    findAmountOfCities(game);

    if (amountOfRedCities == 0) { return Player.BLUE; }
    else if (amountOfBlueCities == 0) { return Player.RED; }
    return null;
  }

  private void findAmountOfCities(GameImpl game) {
    for (Map.Entry<Position, CityImpl> entry : game.getCities().entrySet()) {
      CityImpl city = entry.getValue();
      if (city.getOwner() == Player.RED) {
        amountOfRedCities ++;
      }
      else if (city.getOwner() == Player.BLUE) {
        amountOfBlueCities ++;
      }
    }
  }
}
