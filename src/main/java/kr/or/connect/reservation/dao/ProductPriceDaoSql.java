package kr.or.connect.reservation.dao;

public class ProductPriceDaoSql {
	public static final String SELECT_BY_DISPLAY_INFO_ID = "SELECT product_price.id AS productPriceId, product.id AS productId, product_price.price_type_name AS\r\n"
			+ "priceTypeName, product_price.price, product_price.discount_rate AS discountRate, product_price.create_date\r\n"
			+ "AS createDate, product_price.modify_date AS modifyDate FROM product_price INNER JOIN product ON\r\n"
			+ "product_price.product_id = product.id INNER JOIN display_info ON product.id = display_info.product_id\r\n"
			+ "WHERE display_info.id = :displayInfoId ORDER BY product_price.id DESC";
}
