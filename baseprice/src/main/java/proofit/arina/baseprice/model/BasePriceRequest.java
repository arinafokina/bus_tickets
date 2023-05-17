package proofit.arina.baseprice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePriceRequest {
    private String busTerminalName;

    public BasePriceRequest() {
    }

    public BasePriceRequest(String busTerminalName) {
        this.busTerminalName = busTerminalName;
    }
}
