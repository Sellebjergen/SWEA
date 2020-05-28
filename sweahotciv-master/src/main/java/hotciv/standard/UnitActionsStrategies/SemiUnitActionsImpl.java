package hotciv.standard.UnitActionsStrategies;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.UnitActionsStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;

public class SemiUnitActionsImpl implements UnitActionsStrategy {
  @Override
  public void unitActions(Position p, GameImpl game) {
    if(game.getUnitAt(p).getTypeString().equals(GameConstants.SETTLER)) settlerAction(p, game);
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
}
