package kr.or.connect.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.FileInfo;

@Repository
public class FileInfoDao {
	private SimpleJdbcInsert insertAction;
	
	public FileInfoDao(DataSource dataSource) {
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("file_info").usingGeneratedKeyColumns("id");
	}
	public int insert(FileInfo fileInfo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(fileInfo);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
}
