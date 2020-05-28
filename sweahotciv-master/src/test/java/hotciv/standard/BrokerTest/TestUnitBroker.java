package hotciv.standard.BrokerTest;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.Invoker;

import hotciv.framework.NameService;
import hotciv.broker.main.Broker.NameServiceImpl;
import hotciv.broker.main.Broker.Stubs.StubUnit2;
import hotciv.framework.*;
import hotciv.broker.main.Broker.Invokers.HotCivUnitInvoker;
import hotciv.broker.main.Broker.LocalMethodClientRequestHandler;
import hotciv.broker.main.Broker.SpyStandardJSONRequester;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestUnitBroker {
  private Unit unit;
  private Requestor requestor;
  private NameService nm;

  @Before
  public void setup() {
    nm = new NameServiceImpl();
    Invoker invoker = new HotCivUnitInvoker(nm);
    ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

    nm.putUnit("StubUnit", new StubUnit2(GameConstants.ARCHER, Player.RED));

    requestor = new SpyStandardJSONRequester(crh);
    unit = nm.getUnit("StubUnit");
  }

  @Test
  public void hasTypeString() {
    String type = unit.getTypeString();
    assertThat(type, is(GameConstants.ARCHER));
  }

  @Test
  public void hasOwner() {
    Player owner = unit.getOwner();
    assertThat(owner, is(Player.RED));
  }

  @Test
  public void hasMoveCount() {
    int moveCount = unit.getMoveCount();
    assertThat(moveCount, is(1));
  }

  @Test
  public void hasDefStrength() {
    int defStrength = unit.getDefensiveStrength();
    assertThat(defStrength, is(2));
  }

  @Test
  public void hasAttStrength() {
    int attStrength = unit.getAttackingStrength();
    assertThat(attStrength, is(2));
  }
}