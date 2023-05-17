package proofit.arina.draftprice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Passenger {
    private boolean isChild;
    private int bagsCount;

    public Passenger(boolean isChild, int bagsCount) {
        this.isChild = isChild;
        this.bagsCount = bagsCount;
    }
}
