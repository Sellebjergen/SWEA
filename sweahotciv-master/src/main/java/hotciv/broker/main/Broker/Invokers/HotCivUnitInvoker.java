package hotciv.broker.main.Broker.Invokers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.NameService;
import hotciv.framework.Unit;
import hotciv.broker.main.Broker.BrokerConstants;

public class HotCivUnitInvoker implements Invoker {
  private Gson gson;
  private NameService nm;

  public HotCivUnitInvoker(NameService nm) {
    gson = new Gson();
    this.nm = nm;
  }

  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    if(objectId.equals("")) return new ReplyObject(BrokerConstants.status_OK, gson.toJson(""));

    JsonParser parser = new JsonParser();
    JsonArray array = parser.parse(payload).getAsJsonArray();
    Unit unit = lookupUnit(objectId);

    switch(operationName) {
      case BrokerConstants.getUnitOwner_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(unit.getOwner()));

      case BrokerConstants.getUnitTypeString_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(unit.getTypeString()));

      case BrokerConstants.getMoveCount_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(unit.getMoveCount()));

      case BrokerConstants.getDefensiveStrength_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(unit.getDefensiveStrength()));

      case BrokerConstants.getAttackingStrength_operation:
        return new ReplyObject(BrokerConstants.status_OK, gson.toJson(unit.getAttackingStrength()));

      case BrokerConstants.archerEffect_operation:
        int def = gson.fromJson(array.get(0), Integer.class);
        int move = gson.fromJson(array.get(1), Integer.class);
        unit.archerEffect(def, move);
        return new ReplyObject(BrokerConstants.status_OK, "");
    }

    return null;
  }

  private Unit lookupUnit(String objectId) {
    return nm.getUnit(objectId);
  }
}