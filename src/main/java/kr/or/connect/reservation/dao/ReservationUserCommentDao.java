package kr.or.connect.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ReservationUserComment;

@Repository
public class ReservationUserCommentDao {
	private SimpleJdbcInsert insertAction;
	
	public ReservationUserCommentDao(DataSource dataSource) {
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_user_comment").usingGeneratedKeyColumns("id");
	}
	
	public int insert(ReservationUserComment reservationUserComment) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationUserComment);
		return insertAction.executeAndReturnKey(params).intValue();
	}
}
