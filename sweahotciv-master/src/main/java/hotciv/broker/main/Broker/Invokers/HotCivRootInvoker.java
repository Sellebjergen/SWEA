package hotciv.broker.main.Broker.Invokers;


import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.broker.main.Broker.BrokerConstants;
import hotciv.framework.NameService;
import hotciv.framework.Game;

public class HotCivRootInvoker implements Invoker {
  private NameService nm;
  private Game game;

  public HotCivRootInvoker(Game servant, NameService nm) {
    this.nm = nm;
    game = servant;
  }

  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    String type = operationName.substring(0, operationName.indexOf('_'));
    String operation = operationName.substring(operationName.indexOf('_'));

    switch (type) {
      case BrokerConstants.tileID: return new HotCivTileInvoker(nm).handleRequest(objectId, operation, payload);
      case BrokerConstants.cityID: return new HotCivCityInvoker(nm).handleRequest(objectId, operation, payload);
      case BrokerConstants.unitID: return new HotCivUnitInvoker(nm).handleRequest(objectId, operation, payload);
      case BrokerConstants.gameID: return new HotCivGameInvoker(game, nm).handleRequest(objectId, operation, payload);
    }

    return null;
  }
}
