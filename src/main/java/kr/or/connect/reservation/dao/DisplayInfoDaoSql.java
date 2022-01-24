package kr.or.connect.reservation.dao;

public class DisplayInfoDaoSql {
	public static final String  SELECT_BY_ID = "SELECT product.id AS productId, product.category_id AS categoryId, display_info.id AS displayInfoId, \r\n"
			+ "category.name AS categoryName, product.description AS productDescription, product.content AS productContent,\r\n"
			+ " product.event AS productEvent, opening_hours AS openingHours, place_name AS placeName, \r\n"
			+ " place_lot AS placeLot, place_street AS placeStreet, tel AS telephone, homepage, email, \r\n"
			+ " display_info.create_date AS createDate, display_info.modify_date AS modifyDate FROM display_info LEFT JOIN product ON \r\n"
			+ " product.id = display_info.product_id LEFT JOIN category ON product.category_id = category.id\r\n"
			+ " WHERE  display_info.id = :displayInfoId";
}
