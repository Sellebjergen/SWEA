package hotciv.broker.main.Broker.Invokers;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.NameService;
import hotciv.framework.City;
import hotciv.broker.main.Broker.BrokerConstants;

public class HotCivCityInvoker implements Invoker {
  private Gson gson;
  private NameService nm;

  public HotCivCityInvoker(NameService nm) {
    gson = new Gson();
    this.nm = nm;
  }

  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    City city = lookupCity(objectId);

    switch(operationName) {
      case BrokerConstants.getCityOwner_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(city.getOwner()));

      case BrokerConstants.getSize_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(city.getSize()));

      case BrokerConstants.getTreasury_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(city.getTreasury()));

      case BrokerConstants.getFoodStorage_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(city.getFoodStorage()));

      case BrokerConstants.getProduction_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(city.getProduction()));

      case BrokerConstants.getWorkforceFocus_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(city.getWorkforceFocus()));
    }

    return null;
  }

  private City lookupCity(String objectId) {
    return nm.getCity(objectId);
  }
}
