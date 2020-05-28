package hotciv.standard.AttackingStrategies;

import hotciv.framework.City;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.AttackingStrategy;
import hotciv.standard.UnitImpl;
import hotciv.standard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlphaAttackingStrategyImpl implements AttackingStrategy {
  private ArrayList<HashMap> returns = new ArrayList<>();
  private HashMap<String, Boolean> result = new HashMap<>();

  @Override
  public boolean handleAttackingProcedure(Position from,
                                          Position to,
                                          GameImpl game) {

    // retrieves from the game we've sent with.
    HashMap<Position, UnitImpl> units = game.getUnits();
    HashMap<Position, CityImpl> cities = game.getCities();

    UnitImpl unit = units.get(from);

    if (units.get(to) != null) { units.remove(to); }
    if (cities.get(to) != null) { cities.get(to).changeOwnership(unit.getOwner()); }

    units.remove(from);
    unit.decreaseMoveCount();
    units.put(to, unit);

    game.setCities(cities);
    game.setUnits(units);

    if(game.getAmountOfRounds() > 20) {
      game.incrementCombatWin();
    }

    return true;
  }
}
