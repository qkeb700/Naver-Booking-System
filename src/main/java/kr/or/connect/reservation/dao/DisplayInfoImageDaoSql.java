package kr.or.connect.reservation.dao;

public class DisplayInfoImageDaoSql {
	public static final String SELECT_BY_DISPLAY_INFO_ID = "SELECT display_info_image.id AS displayInfoImageId, display_info.id AS displayInfoId, file_info.id AS\r\n"
			+ "fileId, file_info.file_name AS fileName, file_info.save_file_name AS saveFileName, file_info.content_type\r\n"
			+ "AS contentType, file_info.delete_flag AS deleteFlag, file_info.create_date AS createDate, file_info.modify_date\r\n"
			+ "AS modifyDate FROM display_info_image LEFT JOIN display_info ON display_info_image.display_info_id = \r\n"
			+ "display_info.id LEFT JOIN file_info ON display_info_image.file_id = file_info.id WHERE display_info_id = :displayInfoId";
}
