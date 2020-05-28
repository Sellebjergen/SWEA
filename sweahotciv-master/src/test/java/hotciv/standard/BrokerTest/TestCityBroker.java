package hotciv.standard.BrokerTest;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import frds.broker.Invoker;

import hotciv.framework.NameService;
import hotciv.broker.main.Broker.NameServiceImpl;
import hotciv.broker.main.Broker.Stubs.StubCity5;
import hotciv.framework.*;
import hotciv.broker.main.Broker.Invokers.HotCivCityInvoker;
import hotciv.broker.main.Broker.LocalMethodClientRequestHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestCityBroker {
  private City city;
  private NameService nm;

  @Before
  public void setup() {
    nm = new NameServiceImpl();
    Invoker invoker = new HotCivCityInvoker(nm);
    ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

    nm.putCity("StubCity", new StubCity5(Player.GREEN, 7));

    Requestor requestor = new StandardJSONRequestor(crh);
    city = nm.getCity("StubCity");
  }

  @Test
  public void shouldHaveOwner() {
    Player owner = city.getOwner();
    assertThat(owner, is(Player.GREEN));
  }

  @Test
  public void shouldHaveSize() {
    int size = city.getSize();
    assertThat(size, is(7));
  }

  @Test
  public void shouldHaveTreasury() {
    int treasury = city.getTreasury();
    assertThat(treasury, is(12));
  }

  @Test
  public void shouldFoodStorage() {
    int foodStorage = city.getFoodStorage();
    assertThat(foodStorage, is(6));
  }

  @Test
  public void shouldHaveProduction() {
    String production = city.getProduction();
    assertThat(production, is(GameConstants.ARCHER));
  }

  @Test
  public void shouldHaveWorkFocus() {
    String workFocus = city.getWorkforceFocus();
    assertThat(workFocus, is(GameConstants.foodFocus));
  }
}