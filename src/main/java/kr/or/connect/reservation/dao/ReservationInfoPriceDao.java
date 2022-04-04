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
		this.insert = new SimpleJdbcInsert(dataSource).withTableName("reservation_info_price").usingGeneratedKeyColumns("id");
	}
	
	public int getTotalPrice(int reservationInfoId) {
		List<Integer> totalPrice;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("reservationInfoId", reservationInfoId);
			totalPrice = jdbc.queryForList(ReservationInfoPriceDaoSql.SELECT_TOTAL_PRICE, params, Integer.class);
		} catch(NullPointerException e) {
			totalPrice = null;
		} catch(Exception e) {
			e.printStackTrace();
			totalPrice = null;
		}
		return totalPrice.get(0);
	}
	
	public int insert(ReservationPrice reservationInfoPrice) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfoPrice);
		return insert.executeAndReturnKey(params).intValue();
	}	
	
	public int registerPrice(ReservationPrice reservationPrice) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						"insert into reservation_info_price(reservation_info_id, product_price_id, count) values (?,?,?)", new String[] {"ID"});
				
				pstmt.setInt(1,  reservationPrice.getReservationInfoId());
				pstmt.setInt(2, reservationPrice.getProductPriceId());
				pstmt.setInt(3, reservationPrice.getCount());
				
				return pstmt;
			}
		},keyHolder);
		
		Number keyValue = keyHolder.getKey();
		return keyValue.intValue();
	}
	
//	public List<ReservationPrice> getPriceList(int reservationId) {
//		List<ReservationPrice> priceList = jdbc.query(ReservationInfoPriceDaoSql.SELECT_PRICES_BY_ID, Collections.singletonMap("reservationId", reservationId), rowMapper); 
//		return priceList.isEmpty() ? null : priceList; 
//	}
	public List<ReservationPrice> getPriceList(int reservationInfoId){
		List<ReservationPrice> priceList = jdbcTemplate.query(
				"SELECT id as reservationInfoPriceId, reservation_info_id, product_price_id, count "
				+ "FROM reservation_info_price "
				+ "where reservation_info_id=?",
				new RowMapper<ReservationPrice>() {
					@Override
					public ReservationPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
						ReservationPrice reservationPriceDto = new ReservationPrice();
						reservationPriceDto.setCount(rs.getInt("count"));
						reservationPriceDto.setProductPriceId(rs.getInt("product_price_id"));
						reservationPriceDto.setReservationInfoId(rs.getInt("reservation_info_id"));
						reservationPriceDto.setReservationInfoPriceId(rs.getInt("reservationInfoPriceId"));
						return reservationPriceDto;
					}
				}, reservationInfoId);

		return priceList.isEmpty() ? null : priceList;
	}
}
