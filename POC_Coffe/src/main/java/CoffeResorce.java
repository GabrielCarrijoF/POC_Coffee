import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.logging.Logger;

@Path("/coffee")
public class CoffeResorce {

  private static final Logger LOGGER = Logger.getLogger(CoffeResorce.class);

  @Inject
  CoffeRepositoryService coffeeRepository;

  private AtomicLong counter = new AtomicLong(0);

  @GET
  @Retry(maxRetries = 4)
  public List<Coffe> coffees() {
    final Long invocationNumber = counter.getAndIncrement();

    maybeFail(String.format("CoffeeResource#coffees() invocation #%d failed", invocationNumber));

    LOGGER.infof("CoffeeResource#coffees() invocation #%d returning successfully",
        invocationNumber);
    return coffeeRepository.getAllCoffes();
  }

  private void maybeFail(String failureLogMessage) {
    if (new Random().nextBoolean()) {
      LOGGER.error(failureLogMessage);
      throw new RuntimeException("Resource failure.");
    }
  }
}
