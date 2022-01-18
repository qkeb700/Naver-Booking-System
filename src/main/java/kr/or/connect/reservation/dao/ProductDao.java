package kr.or.connect.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductItems;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);
	
	public ProductDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Product> selectAll(int start){
		Map<String, Object> params = new HashMap<>();
		params.put("start", start);
		
		return jdbc.query(ProductDaoSql.SELECT_ALL, params, rowMapper);
	}
	
	public List<Product> selectAllByCategoryId(int categoryId, int start){
		Map<String, Object> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("start", start);
		
		return jdbc.query(ProductDaoSql.SELECT_ALL_BY_CATEGORY_ID, params, rowMapper);
	}
	
	public int getCount() {
		return jdbc.queryForObject(ProductDaoSql.GET_COUNT, Collections.emptyMap(), int.class);
	}
	
	public int getCountByCategoryId(int categoryId) {
		return jdbc.queryForObject(ProductDaoSql.GET_COUNT_BY_CATEGORY_ID, Collections.singletonMap("categoryId", categoryId), int.class);
	}
	
}
