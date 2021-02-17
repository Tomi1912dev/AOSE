package marketplace;

import energy.Energy;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void addAll(Energy[] e) {
        this.energies.addAll(Arrays.asList(e));
    }

    @Override
    public Iterator<Energy> iterator() {
        return this.energies.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Energy energy: energies) {
            sb.append(energy).append("\n");
        }
        return sb.toString();
    }
}
