package proofit.arina.draftprice.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
public class DraftPriceRequest {
    @NotNull(message = "The 'passengers' parameter is required.")
    @NotEmpty(message = "The 'passengers' must have at least one item.")
    private List<Passenger> passengers;
    @NotNull(message = "The 'date' parameter is required.")
    private Date date;
    @NotNull(message = "The 'busTerminalName' parameter is required.")
    private String busTerminalName;

    public DraftPriceRequest(List<Passenger> passengers, Date date, String busTerminalName) {
        this.passengers = passengers;
        this.date = date;
        this.busTerminalName = busTerminalName;
    }
}
