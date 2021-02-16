package marketplace;

import energy.Energy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarketPlace implements Iterable<Energy> {
    private List<Energy> energies;

    public MarketPlace() {
        this.energies = new ArrayList<>();
    }

    public void add(Energy e) {
        this.energies.add(e);
    }

    public void addAll(List<Energy> e) {
        this.energies.addAll(e);
    }

    @Override
    public Iterator<Energy> iterator() {
        return this.energies.iterator();
    }
}
