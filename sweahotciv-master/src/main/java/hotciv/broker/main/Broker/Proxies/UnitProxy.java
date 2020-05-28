package hotciv.broker.main.Broker.Proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.broker.main.Broker.BrokerConstants;

public class UnitProxy implements Unit, ClientProxy {
  private Requestor requestor;
  private String id;

  UnitProxy(String id, Requestor requestor) {
    this.requestor = requestor;
    this.id = id;
  }

  @Override
  public String getTypeString() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.unitID + BrokerConstants.getUnitTypeString_operation, String.class);
  }

  @Override
  public Player getOwner() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.unitID + BrokerConstants.getUnitOwner_operation, Player.class);
  }

  @Override
  public int getMoveCount() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.unitID + BrokerConstants.getMoveCount_operation, Integer.class);
  }


  @Override
  public int getDefensiveStrength() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.unitID + BrokerConstants.getDefensiveStrength_operation, Integer.class);
  }


  @Override
  public int getAttackingStrength() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.unitID + BrokerConstants.getAttackingStrength_operation, Integer.class);
  }

  @Override
  public void archerEffect(int defence, int moveCount) {
    requestor.sendRequestAndAwaitReply(id, BrokerConstants.unitID + BrokerConstants.archerEffect_operation, void.class, defence, moveCount);
  }

  @Override
  public String getId() {
    return id;
  }
}