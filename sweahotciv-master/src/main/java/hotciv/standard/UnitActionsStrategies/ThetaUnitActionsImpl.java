package hotciv.standard.UnitActionsStrategies;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.UnitActionsStrategy;
import hotciv.framework.Unit;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;

import java.util.HashMap;

public class ThetaUnitActionsImpl implements UnitActionsStrategy {

  @Override
  public void unitActions(Position p, GameImpl game) {
    Unit unit = game.getUnitAt(p);
    switch (unit.getTypeString()) {
      case GameConstants.B52: b52Effect(p, game);
      case GameConstants.ARCHER: archerEffect(p, game);
      case GameConstants.LEGION: legionEffect(p, game);
      case GameConstants.SETTLER: settlerEffect(p, game);
    }
  }

  private void b52Effect(Position p, GameImpl game) {
    HashMap<Position, TileImpl> tiles = game.getTiles();
    HashMap<Position, CityImpl> cities = game.getCities();
    boolean tileIsOfTypeForest = tiles.get(p).getTypeString().equals(GameConstants.FOREST);
    boolean pHasACity = cities.get(p) != null;

    if (tileIsOfTypeForest) {
      tiles.put(p, new TileImpl(GameConstants.PLAINS));
      game.setTiles(tiles);
      return;
    } else if (pHasACity) {
      if (cities.get(p).getSize() < 2) {
        cities.remove(p);
        return;
      }
      cities.get(p).decreasePopulation();
      game.setCities(cities);
      return;
    }
  }

  private void archerEffect(Position p, GameImpl game) {
    // if the moveCount is below one (the archer moved this round) don't do shiet
    if (game.getUnitAt(p).getMoveCount() > 0) {
      // if the archer hasn't moved, go into fortify mode.
      game.getUnitAt(p).archerEffect(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER) * 2, 0);
    }

    // if already active, reset
    else if (game.getUnitAt(p).getDefensiveStrength() == GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER) * 2) {
      game.getUnitAt(p).archerEffect(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER), GameConstants.landMoveCount);
    }
  }

  private void legionEffect(Position p, GameImpl game) {
    // doesn't do anything yet.
  }

  private void settlerEffect(Position p, GameImpl game) {
    // if the settler haven't moved this turn
    if (game.getUnitAt(p).getMoveCount() > 0) {
      // create city
      game.getCities().put(p, new CityImpl(game.getUnitAt(p).getOwner(), GameConstants.productionFocus, GameConstants.ARCHER));
      // remove unit
      game.getUnits().remove(p);
    }
  }
}
