import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoffeRepositoryService {

  private Map<Integer, Coffe> coffeList = new HashMap<>();

    public CoffeRepositoryService() {
      coffeList.put(1, new Coffe(1, "Fernandez Espresso", "Colombia", 23));
      coffeList.put(2, new Coffe(2, "La Scala Whole Beans", "Bolivia", 18));
      coffeList.put(3, new Coffe(3, "Dak Lak Filter", "Vietnam", 25));
    }

    public List<Coffe> getAllCoffes() {
      return new ArrayList<>(coffeList.values());
    }

    public Coffe getCoffeById(Integer id) {
      return coffeList.get(id);
    }

    public List<Coffe> getRecommendations(Integer id) {
      if (id == null) {
        return Collections.emptyList();
      }
      return coffeList.values().stream()
          .filter(coffee -> !id.equals(coffee.id))
          .limit(2)
          .collect(Collectors.toList());
    }
  }