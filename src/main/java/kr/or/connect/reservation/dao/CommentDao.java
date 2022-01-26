package kr.or.connect.reservation.dao;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Comment;

@Repository
public class CommentDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Comment> rowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
	
	public CommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Comment> selectByDisplayInfoId(int displayInfoId) {
		return jdbc.query(CommentDaoSql.SELECT_BY_DISPLAY_INFO_ID, Collections.singletonMap("displayInfoId", displayInfoId), rowMapper);
	}
	
	public double selectAvgScoreByDisplayInfoId(int displayInfoId) {
		return jdbc.queryForObject(CommentDaoSql.SELECT_AVG_SCORE_BY_DISPLAY_INFO_ID, Collections.singletonMap("displayInfoId", displayInfoId), Double.class);
	}
}
