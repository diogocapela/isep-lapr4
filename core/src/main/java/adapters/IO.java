package adapters;

import java.util.List;

public interface IO<T extends Exporter> {
    List<String[]> importObject(int expectedColumns);

    boolean exportObject(T obj);
}
