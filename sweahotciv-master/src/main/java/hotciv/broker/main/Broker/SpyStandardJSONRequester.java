package hotciv.broker.main.Broker;

import frds.broker.ClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;

import java.lang.reflect.Type;

public class SpyStandardJSONRequester extends StandardJSONRequestor {
  private String objectID;
  private String operationName;
  private Type typeOfReturnValue;
  private Object[] argument;

  public SpyStandardJSONRequester(ClientRequestHandler crh) {
    super(crh);
  }

  @Override
  public <T> T sendRequestAndAwaitReply(String objectId,
                                        String operationName,
                                        Type typeOfReturnValue,
                                        Object... argument) {
    this.objectID = objectId;
    this.operationName = operationName;
    this.typeOfReturnValue = typeOfReturnValue;
    this.argument = argument;
    return super.sendRequestAndAwaitReply(objectId, operationName,typeOfReturnValue, argument);
  }

  public String lastOperationName() {
    return operationName;
  }

  public Object[] getArguments() {
    return argument;
  }
}