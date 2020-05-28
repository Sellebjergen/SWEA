package hotciv.framework.StrategyInterfaces;

import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public interface WorldLayoutStrategy {
  public void putTiles(GameImpl game);

  public void putCities(GameImpl game);

  public void putUnits(GameImpl game);
}
