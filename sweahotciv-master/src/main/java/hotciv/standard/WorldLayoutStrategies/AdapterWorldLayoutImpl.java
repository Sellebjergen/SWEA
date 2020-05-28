package hotciv.standard.WorldLayoutStrategies;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.WorldLayoutStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.HashMap;

public class AdapterWorldLayoutImpl implements WorldLayoutStrategy {

  @Override
  public void putTiles(GameImpl game) {
    ThirdPartyFractalGenerator landscape = new ThirdPartyFractalGenerator();
    HashMap<Position, TileImpl> worldTiles = new HashMap<>();
    for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
      for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
        String type = "error";
        char tileChar = landscape.getLandscapeAt(r, c);
        if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
        if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
        if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
        if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
        if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
        Position p = new Position(r,c);
        worldTiles.put( p, new TileImpl(type));
      }
    }
    game.setTiles(worldTiles);
  }

  // just copied from delta, to make the world more liveable.

  @Override
  public void putCities(GameImpl game) {
    HashMap<Position, CityImpl> worldCities = new HashMap<>();
    worldCities.put(new Position(8, 12), new CityImpl(Player.RED, GameConstants.productionFocus, GameConstants.ARCHER));
    worldCities.put(new Position(4, 5), new CityImpl(Player.BLUE, GameConstants.foodFocus, GameConstants.LEGION));

    game.setCities(worldCities);
  }

  @Override
  public void putUnits(GameImpl game) {
    HashMap<Position, UnitImpl> worldUnits = new HashMap<>();
    worldUnits.put(new Position(3, 8), new UnitImpl(GameConstants.ARCHER, Player.RED));
    worldUnits.put(new Position(4, 4), new UnitImpl(GameConstants.LEGION, Player.BLUE));

    game.setUnits(worldUnits);
  }
}
