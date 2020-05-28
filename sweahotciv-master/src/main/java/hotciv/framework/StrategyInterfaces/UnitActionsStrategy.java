package hotciv.framework.StrategyInterfaces;

import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;
import java.util.List;

public interface UnitActionsStrategy {
  void unitActions(Position p, GameImpl game);
}
