package hotciv.framework.StrategyInterfaces;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;

import java.util.HashMap;

public interface WinningStrategy {
  public Player getWinner(GameImpl game);
}
