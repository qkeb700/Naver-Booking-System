package kr.or.connect.reservation.dao;

public class ProductImageDaoSql {
	public static final String SELECT_BY_DISPLAY_INFO_ID = "SELECT product.id AS productID, product_image.id AS productImageId, product_image.type, file_info.id \r\n"
			+ " AS fileInfoId, file_info.file_name AS fileName, file_info.save_file_name AS saveFileName, \r\n"
			+ " file_info.content_type AS contentType, file_info.delete_flag AS deleteFlag, file_info.create_date AS\r\n"
			+ " createDate, file_info.modify_date AS modifyDate FROM product_image LEFT JOIN product ON \r\n"
			+ " product.id = product_image.product_id LEFT JOIN file_info ON product_image.file_id = file_info.id \r\n"
			+ " LEFT JOIN display_info ON product.id = display_info.product_id WHERE product_image.type IN ('ma', 'et') AND display_info.id = :displayInfoId";
}
