package framework;

import java.util.HashMap;
import java.util.Map;

public class DependencyContainer {

    Map<String, Class> quilifierMap;

    public DependencyContainer() {
        quilifierMap = new HashMap<>();
    }

    public void add(String newValue, Class newClass) {
        quilifierMap.put(newValue, newClass);
    }

    public Map<String, Class> getQuilifierMap() {
        return quilifierMap;
    }

    public void setQuilifierMap(Map<String, Class> quilifierMap) {
        this.quilifierMap = quilifierMap;
    }
}
