package hotciv.broker.main.Broker.Invokers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.broker.main.Broker.BrokerConstants;
import hotciv.framework.NameService;
import hotciv.framework.*;

public class HotCivGameInvoker implements Invoker {
  private Game game;
  private Gson gson;
  private NameService nm;

  HotCivGameInvoker(Game servant, NameService nm) {
    this.nm = nm;
    this.gson = new Gson();
    this.game = servant;
  }

  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    JsonParser parser = new JsonParser();
    JsonArray array = parser.parse(payload).getAsJsonArray();

    switch (operationName) {
      case BrokerConstants.getTile_operation:
        Position tilePos = gson.fromJson(array.get(0), Position.class);
        Tile tile = game.getTileAt(tilePos);
        String tile_id = tile.getId();
        nm.putTile(tile_id, tile);
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(tile_id));

      case BrokerConstants.getUnit_operation:
        Position unitPos = gson.fromJson(array.get(0), Position.class);
        Unit unit = game.getUnitAt(unitPos);
        if(unit == null) return new ReplyObject(BrokerConstants.status_OK, gson.toJson(""));
        String unit_id = unit.getId();
        nm.putUnit(unit_id, unit);
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(unit_id));

      case BrokerConstants.getCity_operation:
        Position cityPos = gson.fromJson(array.get(0), Position.class);
        City city = game.getCityAt(cityPos);
        if(city == null) return new ReplyObject(BrokerConstants.status_OK, gson.toJson(""));
        String city_id = city.getId();
        nm.putCity(city_id, city);
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(city_id));

      case BrokerConstants.getWinner_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(game.getWinner()));

      case BrokerConstants.getPlayerInTurn_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(game.getPlayerInTurn()));

      case BrokerConstants.getAge_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(game.getAge()));

      case BrokerConstants.endOfTurn_operation:
        game.endOfTurn();
        return new ReplyObject(BrokerConstants.status_OK, "");

      case BrokerConstants.moveUnit_operation:
        Position from = gson.fromJson(array.get(0), Position.class);
        Position to = gson.fromJson(array.get(1), Position.class);
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(game.moveUnit(from, to)));

      case BrokerConstants.changeWorkFocus_operation:
        cityPos = gson.fromJson(array.get(0), Position.class);
        String newFocus = gson.fromJson(array.get(1), String.class);
        game.changeWorkForceFocusInCityAt(cityPos, newFocus);
        return new ReplyObject(BrokerConstants.status_OK, "");

      case BrokerConstants.changeProduction_operation:
        cityPos = gson.fromJson(array.get(0), Position.class);
        String newProduction = gson.fromJson(array.get(1), String.class);
        game.changeProductionInCityAt(cityPos, newProduction);
        return new ReplyObject(BrokerConstants.status_OK, "");

      case BrokerConstants.performUnitAction_operation:
        unitPos = gson.fromJson(array.get(0), Position.class);
        game.performUnitActionAt(unitPos);
        return new ReplyObject(BrokerConstants.status_OK, "");
    }

    return null;
  }
}