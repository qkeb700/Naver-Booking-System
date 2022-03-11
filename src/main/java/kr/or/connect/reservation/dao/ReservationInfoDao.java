package kr.or.connect.reservation.dao;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ReservationInfo;

@Repository
public class ReservationInfoDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationInfo> rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
	private SimpleJdbcInsert insertAction;
	
	public ReservationInfoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("Reservation_info").usingGeneratedKeyColumns("id").usingColumns(
							"product_id", "display_info_id", "reservation_name", "reservation_tel", "reservation_email", "reservation_date",
							"create_date", "modify_date");
	}
	public List<ReservationInfo> selectByEmail(String reservationEmail) {
		return jdbc.query(ReservationInfoDaoSql.SELECT_BY_EMAIL, Collections.singletonMap("reservationEmail", reservationEmail), rowMapper);
	}
	
	public int selectTotalPrice() {
		
	}
}
