package socialnetwork;

import socialnetwork.domain.Board;
import socialnetwork.domain.Message;

import java.util.List;

public class FineBoard extends FineSet<Message> implements Board {

  public FineBoard() { super(); }

  @Override
  public boolean addMessage(Message message) {
    return super.addItem(message, message.getMessageId());
  }

  @Override
  public boolean deleteMessage(Message message) {
    return super.removeItem(message, message.getMessageId());
  }

  @Override
  public int size() {
    return super.size();
  }

  @Override
  public List<Message> getBoardSnapshot() {
    return super.getSnapshot();
  }
}
