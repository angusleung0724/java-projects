package museumvisit;

import java.util.ArrayList;
import java.util.List;

public class Exit extends MuseumSite {

  public Exit() {
    super("Exit");
  }

  @Override
  public List<Turnstile> getExitTurnstiles() {
    return new ArrayList<>();
  }
}
