package hotciv.standard.WinningStrategies;

import hotciv.framework.Player;
import hotciv.framework.StrategyInterfaces.WinningStrategy;
import hotciv.standard.GameImpl;

public class EpsilonWinningStrategyImpl implements WinningStrategy {

  @Override
  public Player getWinner(GameImpl game) {
    if(game.getRedCombatWins() >= 3) return Player.RED;
    if(game.getBlueCombatWins() >= 3) return Player.BLUE;
    return null;
  }
}
