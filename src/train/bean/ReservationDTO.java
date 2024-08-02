package train.bean;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {
	private String res_id;
	private String user_id;
	private String train_id;
	private int res_adult;
	private int res_child;
	private int res_total;
	private Timestamp res_time;
	private String res_status;
}
