package kr.or.connect.reservation.dao;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.DisplayInfo;

@Repository
public class DisplayInfoDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<DisplayInfo> rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	
	public DisplayInfoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	public DisplayInfo selectById(int displayInfoId) {
		return jdbc.queryForObject(DisplayInfoDaoSql.SELECT_BY_ID, Collections.singletonMap("displayInfoId", displayInfoId), rowMapper);
	}
}
