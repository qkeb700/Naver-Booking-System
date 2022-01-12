package kr.or.connect.reservation.dao;

public class CategoryDaoSql {
	public static final String SELECT_CATEGORY_LIST = "SELECT category.id, category.name ,COUNT(*) AS count FROM category \r\n"
			+ "INNER JOIN product ON category.id = product.category_id \r\n"
			+ "INNER JOIN display_info ON product.id = display_info.product_id \r\n"
			+ "group by category.id, category.name";
}
