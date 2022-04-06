package kr.or.connect.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoPriceDto;

@Repository
public class ReservationInfoDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationInfo> rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
	private SimpleJdbcInsert insertAction;
	
	
	@Autowired
	public ReservationInfoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_info").usingGeneratedKeyColumns("id").usingColumns(
							"product_id", "display_info_id", "reservation_name", "reservation_tel", "reservation_email", "reservation_date",
							"cancel_flag", "create_date", "modify_date");
	}
	public List<ReservationInfo> selectByEmail(String reservationEmail) {
		List<ReservationInfo> infoList = jdbc.query(ReservationInfoDaoSql.SELECT_BY_EMAIL, Collections.singletonMap("reservationEmail", reservationEmail), rowMapper);
		return infoList.isEmpty()?null:infoList;
	}
	
	
	public int selectTotalPrice(String reservationEmail, int productId, int displayInfoId) {
		int totalPrice;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("reservationEmail", reservationEmail);
			params.put("productId", productId);
			params.put("displayInfoId", displayInfoId);
			totalPrice = jdbc.queryForObject(ReservationInfoDaoSql.SELECT_TOTAL_PRICE, params, Integer.class);			
		} catch(NullPointerException e) {
			totalPrice = 0;
		} catch(Exception e) {
			e.printStackTrace();
			totalPrice = 0;
		}
		return totalPrice;
	}
	public int selectIdByEmail(String reservationEmail) {
		int id;
		try { 
			id = jdbc.queryForObject(ReservationInfoDaoSql.SELECT_ID_BY_EMAIL, Collections.singletonMap("reservationEmail", reservationEmail), Integer.class);
		}catch(NullPointerException e) {
			id = 0;
		}catch(Exception e) {
			e.printStackTrace();
			id = 0;
		}
		return id;
	}
	public ReservationInfo getReservationInfo(int reservationId) {
		List<ReservationInfo> reservationInfoList = jdbc.query(ReservationInfoDaoSql.SELECT_BY_Id, Collections.singletonMap("reservationInfoId", reservationId), rowMapper);
		return reservationInfoList.isEmpty() ? null : reservationInfoList.get(0);
	}
	public int updateCancelFlagById(int reservationInfoId, int cancelFlag) {
		Map<String, Object> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);
		params.put("cancelFlag", cancelFlag);
		return jdbc.update(ReservationInfoDaoSql.UPDATE_CANCEL_FLAG_BY_ID, params);
	} 
	public int insert(ReservationInfoPriceDto reservationInfo) {
		Map<String, Object> params = new HashMap<>();
	    params.put("product_id", reservationInfo.getProductId());
	    params.put("display_info_id", reservationInfo.getDisplayInfoId());
	    params.put("reservation_name", reservationInfo.getReservationName());
	    params.put("reservation_tel", reservationInfo.getReservationTel());
	    params.put("reservation_email", reservationInfo.getReservationEmail());
	    params.put("reservation_date", reservationInfo.getReservationDate());
	    params.put("cancel_flag", reservationInfo.getCancelFlag());
	    params.put("create_date", reservationInfo.getCreateDate());
	    params.put("modify_date", reservationInfo.getModifyDate());

	    return insertAction.executeAndReturnKey(params).intValue();
	}
	
	class reservationInfoMapper implements RowMapper<ReservationInfo> {
		@Override
		public ReservationInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReservationInfo reservationInfoDto = new ReservationInfo();
			reservationInfoDto.setCancelFlag(rs.getInt("cancel_flag"));
			reservationInfoDto.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			reservationInfoDto.setDisplayInfoId(rs.getInt("displayInfoId"));
			reservationInfoDto.setProductId(rs.getInt("productId"));
			reservationInfoDto.setReservationDate(rs.getTimestamp("reservation_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			reservationInfoDto.setReservationInfoId(rs.getInt("reservationInfoId"));
			reservationInfoDto.setReservationName(rs.getString("reservation_name"));
			reservationInfoDto.setReservationTel(rs.getString("reservation_tel"));
			reservationInfoDto.setReservationEmail(rs.getString("reservation_email"));
			reservationInfoDto.setModifyDate(rs.getTimestamp("modify_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			return reservationInfoDto;
		}
	}
}
