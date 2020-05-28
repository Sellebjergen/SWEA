package hotciv.standard.WorldLayoutStrategies;

import hotciv.framework.Player;
import hotciv.framework.StrategyInterfaces.WorldLayoutStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class AlphaWorldLayoutImpl implements WorldLayoutStrategy {
  public void putTiles(GameImpl game) {
    HashMap<Position, TileImpl> worldTiles = new HashMap<>();
    for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
      for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
        worldTiles.put(new Position(r, c), new TileImpl(GameConstants.PLAINS));
      }
    }
    worldTiles.put(new Position(0, 1), new TileImpl(GameConstants.HILLS));
    worldTiles.put(new Position(1, 0), new TileImpl(GameConstants.OCEANS));
    worldTiles.put(new Position(2, 2), new TileImpl(GameConstants.MOUNTAINS));

    game.setTiles(worldTiles);
  }

  public void putCities(GameImpl game) {
    HashMap<Position, CityImpl> worldCities = new HashMap<>();
    worldCities.put(new Position(1, 1), new CityImpl(Player.RED, GameConstants.productionFocus, GameConstants.ARCHER));
    worldCities.put(new Position(4, 1), new CityImpl(Player.BLUE, GameConstants.foodFocus, GameConstants.LEGION));

    game.setCities(worldCities);
  }

  public void putUnits(GameImpl game) {
    HashMap<Position, UnitImpl> worldUnits = new HashMap<>();
    worldUnits.put(new Position(2, 0), new UnitImpl(GameConstants.ARCHER, Player.RED));
    worldUnits.put(new Position(3, 2), new UnitImpl(GameConstants.LEGION, Player.BLUE));
    worldUnits.put(new Position(4, 3), new UnitImpl(GameConstants.SETTLER, Player.RED));

    game.setUnits(worldUnits);
  }
}
