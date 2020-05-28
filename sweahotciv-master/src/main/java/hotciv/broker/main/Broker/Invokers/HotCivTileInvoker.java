package hotciv.broker.main.Broker.Invokers;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.NameService;
import hotciv.framework.Tile;
import hotciv.broker.main.Broker.BrokerConstants;

public class HotCivTileInvoker implements Invoker {
  private Gson gson;
  private NameService nm;

  public HotCivTileInvoker(NameService nm) {
    gson = new Gson();
    this.nm = nm;
  }

  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    Tile tile = lookupTile(objectId);

    if (BrokerConstants.getTileTypeString_operation.equals(operationName)) {
      return new ReplyObject(BrokerConstants.status_OK, gson.toJson(tile.getTypeString()));
    }

    return null;
  }

  private Tile lookupTile(String objectId) {
    return nm.getTile(objectId);
  }
}