package hotciv.standard.MovingStrategies;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.MovingStrategy;

public class groundMovingStrategyImpl implements MovingStrategy {
  @Override
  public boolean isAValidMovement(Position from, Position to, Game game) {
    // checks that there is an unit and the to tile is valid
    boolean unitIsNULL = game.getUnitAt(from) == null;
    if (unitIsNULL) { return false; }

    boolean unitMoveCountbelow1 = game.getUnitAt(from).getMoveCount() < 1;
    if (unitMoveCountbelow1) { return false; }

    boolean toTileIsOcean = game.getTileAt(to).getTypeString().equals(GameConstants.OCEANS);
    if (toTileIsOcean) { return false; }

    boolean toTileIsMountain = game.getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
    if (toTileIsMountain) { return false; }

    boolean unitOnToTileHaveTheSameOwner = game.getUnitAt(to) != null &&
            game.getUnitAt(from).getOwner().equals(game.getUnitAt(to).getOwner());
    if (unitOnToTileHaveTheSameOwner) { return false;}

    boolean itsYourUnit = game.getUnitAt(from).getOwner().equals(game.getPlayerInTurn());
    if (!itsYourUnit) { return false; }

    // checks that the "to" tile is at most 1 tile from the "from" tile.
    // otherwise return false
    return toTileIsAtMost1TileAway(from, to);
  }

  private boolean toTileIsAtMost1TileAway(Position from, Position to) {
    for (Position p: hotciv.utility.Utility.get8neighborhoodOf(from)) {
      if (to.equals(p)) { return true; }
    }
    return false;
  }
}
