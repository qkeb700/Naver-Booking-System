package kr.or.connect.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ReservationInfoPriceDto;
import kr.or.connect.reservation.dto.ReservationPrice;

@Repository
public class ReservationInfoPriceDao {
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insert;
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationPrice> rowMapper = BeanPropertyRowMapper.newInstance(ReservationPrice.class);
	@Autowired
	public ReservationInfoPriceDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
//		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insert = new SimpleJdbcInsert(dataSource).withTableName("reservation_info_price").usingGeneratedKeyColumns("id");
	}
	
//	public int getTotalPrice(int reservationInfoId) {
//		List<Integer> totalPrice;
//		try {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("reservationInfoId", reservationInfoId);
//			totalPrice = jdbc.queryForList(ReservationInfoPriceDaoSql.SELECT_TOTAL_PRICE, Collections.singletonMap("reservationInfoId", reservationInfoId), Integer.class);
//		} catch(NullPointerException e) {
//			totalPrice = null;
//		} catch(Exception e) {
//			e.printStackTrace();
//			totalPrice = null;
//		}
//		return totalPrice.isEmpty()?null: totalPrice.get(0);
//	}
	public int getTotalPrice(int reservationInfoId) {
		List<Integer> totalPrice = jdbcTemplate.query(
				"select SUM(price*count) as totalPrice from reservation_info " + 
				"INNER JOIN reservation_info_price ON reservation_info_price.reservation_info_id = reservation_info.id " + 
				"INNER JOIN product_price ON product_price.id = reservation_info_price.product_price_id " + 
				"WHERE reservation_info_id = ? ",
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt("totalPrice");
					}
				}, reservationInfoId);

		return totalPrice.isEmpty() ? null : totalPrice.get(0);
	}
	
	public int insert(ReservationPrice reservationInfoPrice) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfoPrice);
		return insert.executeAndReturnKey(params).intValue();
	}	
	
	public List<ReservationPrice> getPriceList(int reservationId) {
		List<ReservationPrice> priceList = jdbc.query(ReservationInfoPriceDaoSql.SELECT_PRICES_BY_ID, Collections.singletonMap("reservationId", reservationId), rowMapper); 
		return priceList.isEmpty() ? null : priceList; 
	}
}
