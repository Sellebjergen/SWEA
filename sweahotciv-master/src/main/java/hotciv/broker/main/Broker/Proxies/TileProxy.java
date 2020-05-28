package hotciv.broker.main.Broker.Proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.Tile;
import hotciv.broker.main.Broker.BrokerConstants;

public class TileProxy implements Tile, ClientProxy {
  private Requestor requestor;
  private String id;

  TileProxy(String id, Requestor requestor) {
    this.id = id;
    this.requestor = requestor;
  }

  @Override
  public String getTypeString() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.tileID + BrokerConstants.getTileTypeString_operation, String.class);
  }

  @Override
  public String getId() {
    return id;
  }
}
