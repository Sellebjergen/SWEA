package hotciv.broker.main.Broker;

import frds.broker.ClientRequestHandler;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;

import frds.broker.Invoker;

public class LocalMethodClientRequestHandler implements ClientRequestHandler {
  private Invoker invoker;
  private ReplyObject lastReply;
  private RequestObject lastRequest;

  public LocalMethodClientRequestHandler(Invoker invoker) {
    this.invoker = invoker;
  }

  @Override
  public ReplyObject sendToServer(RequestObject requestObject) {
    lastRequest = requestObject;
    lastReply = invoker.handleRequest(requestObject.getObjectId(),
                                      requestObject.getOperationName(),
                                      requestObject.getPayload());
    return lastReply;
  }

  @Override
  public void setServer(String hostname, int port) {
    // not used (yet)
  }

  @Override
  public void close() {
    // not used (yet)
  }
}
