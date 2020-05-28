package hotciv.standard.UnitActionsStrategies;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.UnitActionsStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GammaUnitActionsImpl implements UnitActionsStrategy {

  @Override
  public void unitActions(Position p, GameImpl game) {
    // settlers action:
    if(game.getUnitAt(p).getTypeString().equals(GameConstants.SETTLER)) {
      settlerAction(p, game);
    }

    // archers action:
    else if (game.getUnitAt(p).getTypeString().equals(GameConstants.ARCHER)) {
      archerAction(p, game);
    }
  }

  private void settlerAction(Position p, GameImpl game) {
    // if the settler haven't moved this turn
    if (game.getUnitAt(p).getMoveCount() > 0) {
      // create city
      game.getCities().put(p, new CityImpl(game.getUnitAt(p).getOwner(), GameConstants.productionFocus, GameConstants.ARCHER));
      // remove unit
      game.getUnits().remove(p);
    }
  }

  private void archerAction(Position p, GameImpl game) {
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
}
