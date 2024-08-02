package train.bean;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {

	private String train_id, train_name, depature_station, arrival_station;
	private int max_seat, type_adult, type_kid;
	private Timestamp departure_time;
	
	
}
