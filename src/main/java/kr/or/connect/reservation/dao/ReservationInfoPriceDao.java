package kr.or.connect.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class ReservationInfoPriceDao {
	private SimpleJdbcInsert insert;
	public ReservationInfoPriceDao(DataSource dataSource) {
		this.insert = new SimpleJdbcInsert(dataSource).withTableName("reservation_info_price").usingGeneratedKeyColumns("id");
	}
}
