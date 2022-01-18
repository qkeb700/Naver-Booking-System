package kr.or.connect.reservation.dao;

public class ProductDaoSql {
	public static final String SELECT_ALL = "SELECT display_info.id as displayInfoId, product.id as productId, product.description as productDescription,\r\n"
			+ "display_info.place_name as placeName, product.content as productContent, file_info.save_file_name as productImageUrl\r\n"
			+ "from product LEFT JOIN display_info ON product.id = display_info.product_id LEFT JOIN product_image ON\r\n"
			+ "product.id = product_image.product_id LEFT JOIN file_info ON product_image.file_id = file_info.id\r\n"
			+ "where product_image.type = 'th' limit :start	, 4"; 
	
	public static final String SELECT_ALL_BY_CATEGORY_ID = "SELECT display_info.id as displayInfoId, product.id as productId, product.description as productDescription,\r\n"
			+ "display_info.place_name as placeName, product.content as productContent, file_info.save_file_name as productImageUrl\r\n"
			+ "from product LEFT JOIN display_info ON product.id = display_info.product_id LEFT JOIN product_image ON\r\n"
			+ "product.id = product_image.product_id LEFT JOIN file_info ON product_image.file_id = file_info.id\r\n"
			+ "where product.category_id = :categoryId AND product_image.type = 'th' limit :start, 4";
	
	public static final String GET_COUNT = "SELECT COUNT(*) as count FROM product LEFT JOIN display_info ON product.id = display_info.product_id LEFT JOIN product_image ON\r\n"
			+ "product.id = product_image.product_id LEFT JOIN file_info ON product_image.file_id = file_info.id\r\n"
			+ "where product_image.type = 'th'";
	
	public static final String GET_COUNT_BY_CATEGORY_ID = "SELECT COUNT(*) as count FROM product LEFT JOIN display_info ON product.id = display_info.product_id LEFT JOIN product_image ON\r\n"
			+ "product.id = product_image.product_id LEFT JOIN file_info ON product_image.file_id = file_info.id\r\n"
			+ "where product_image.type = 'th' AND product.category_id = :categoryId";
}
