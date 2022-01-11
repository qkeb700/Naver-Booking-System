package kr.or.connect.reservation.dao;

public class CategoryDaoSql {
	public static final String SELECT_CATEGORY_LIST = "SELECT category.id, category.name, COUNT(*) AS count FROM category INNER JOIN product ON category.id = product.caategory_id INNER JOIN display_info ON product.id = display_info.product_id GROUP BY category.id, category.name";
}
