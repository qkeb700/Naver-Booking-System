package kr.or.connect.reservation.dao;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kr.or.connect.reservation.dto.ReservationInfoPriceDto;
import kr.or.connect.reservation.dto.ReservationPrice;

public class ReservationInfoPriceDao {
	private SimpleJdbcInsert insert;
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationPrice> rowMapper = BeanPropertyRowMapper.newInstance(ReservationPrice.class);
	public ReservationInfoPriceDao(DataSource dataSource) {
		this.insert = new SimpleJdbcInsert(dataSource).withTableName("reservation_info_price").usingGeneratedKeyColumns("id");
	}
	public Long insert(ReservationPrice reservationInfoPrice) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfoPrice);
		return insert.executeAndReturnKey(params).longValue();
	}
	public List<ReservationPrice> getPriceList(int reservationId) {
		List<ReservationPrice> priceList = jdbc.query(ReservationInfoPriceDaoSql.SELECT_PRICES_BY_ID, Collections.singletonMap("reservationId", reservationId), rowMapper); 
		return priceList.isEmpty() ? null : priceList; 
	}
}
