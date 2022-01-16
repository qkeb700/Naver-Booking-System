package kr.or.connect.reservation.dao;

public class PromotionDaoSql {
	public static final String SELECT_ALL = "select promotion.id, promotion.product_id as productId, file_info.save_file_name as\r\n"
			+ "productImageUrl, product.description, display_info.place_name as placeName from promotion join product on promotion.product_id = product.id\r\n"
			+ "inner join display_info on product.id = display_info.product_id\r\n"
			+ "inner join product_image on promotion.product_id = product_image.product_id \r\n"
			+ "and product_image.type = 'th' inner join file_info on product_image.file_id =\r\n"
			+ "file_info.id;";
}
