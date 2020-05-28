package hotciv.standard.WinningStrategies;

import hotciv.framework.Player;
import hotciv.framework.StrategyInterfaces.WinningStrategy;
import hotciv.standard.GameImpl;

public class ZetaWinningStrategyImpl implements WinningStrategy {
  private WinningStrategy beta;
  private WinningStrategy epsilon;
  private WinningStrategy current;

  public ZetaWinningStrategyImpl(WinningStrategy beta,
                                 WinningStrategy epsilon) {
    this.beta = beta;
    this.epsilon = epsilon;
    this.current = null;
  }

  @Override
  public Player getWinner(GameImpl game) {
    if (game.getAmountOfRounds() <= 20) this.current = beta;
    else if(game.getAmountOfRounds() > 20) this.current = epsilon;
    return current.getWinner(game);
  }
}
