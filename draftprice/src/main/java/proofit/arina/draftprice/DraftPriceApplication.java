package proofit.arina.draftprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({@ComponentScan("proofit.arina.draftprice.controller")})
public class DraftPriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DraftPriceApplication.class, args);
	}

}
