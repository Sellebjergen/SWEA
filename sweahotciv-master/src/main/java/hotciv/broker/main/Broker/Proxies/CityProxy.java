package hotciv.broker.main.Broker.Proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.broker.main.Broker.BrokerConstants;

public class CityProxy implements City, ClientProxy {
  private Requestor requestor;
  private String id;

  CityProxy(String id, Requestor requestor) {
    this.requestor = requestor;
    this.id = id;
  }

  @Override
  public Player getOwner() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.cityID + BrokerConstants.getCityOwner_operation, Player.class);
  }

  @Override
  public int getSize() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.cityID + BrokerConstants.getSize_operation, Integer.class);
  }

  @Override
  public int getTreasury() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.cityID + BrokerConstants.getTreasury_operation, Integer.class);
  }

  @Override
  public int getFoodStorage() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.cityID + BrokerConstants.getFoodStorage_operation, Integer.class);
  }

  @Override
  public String getProduction() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.cityID + BrokerConstants.getProduction_operation, String.class);
  }

  @Override
  public String getWorkforceFocus() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.cityID + BrokerConstants.getWorkforceFocus_operation, String.class);
  }

  @Override
  public String getId() {
    return id;
  }
}