package hotciv.framework.StrategyInterfaces;

import hotciv.framework.Game;
import hotciv.framework.Position;

public interface MovingStrategy {
  boolean isAValidMovement(Position from, Position to, Game game);
}
