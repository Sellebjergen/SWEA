package hotciv.standard.WinningStrategies;

import hotciv.framework.Player;
import hotciv.framework.StrategyInterfaces.WinningStrategy;
import hotciv.standard.GameImpl;

import java.util.HashMap;

public class AlphaWinningStrategyImpl implements WinningStrategy {

  @Override
  public Player getWinner(GameImpl game) {
    if(game.getAge() >= -3000) {
      return Player.RED;
    }
    return null;
  }

}
