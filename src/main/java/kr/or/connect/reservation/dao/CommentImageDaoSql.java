package kr.or.connect.reservation.dao;

public class CommentImageDaoSql {
	public static final String SELECT_BY_DISPLAY_INFO_ID = "SELECT file_info.content_type AS contentType, file_info.create_date AS createDate, file_info.delete_flag\r\n"
			+ "AS deleteFlag, file_info.id AS fileId, file_info.file_name AS fileName, reservation_user_comment_image.id\r\n"
			+ "AS imageId, file_info.modify_date AS modifyDate, reservation_info.id AS reservationInfoId, \r\n"
			+ "reservation_user_comment.id AS reservationUserCommentId, file_info.save_file_name AS saveFileName\r\n"
			+ "FROM reservation_user_comment_image LEFT JOIN file_info ON reservation_user_comment_image.file_id =\r\n"
			+ "file_info.id LEFT JOIN reservation_info ON reservation_user_comment_image.reservation_info_id =\r\n"
			+ "reservation_info.id LEFT JOIN reservation_user_comment ON reservation_user_comment_image.reservation_user_comment_id =\r\n"
			+ "reservation_user_comment.id LEFT JOIN display_info ON reservation_info.display_info_id = display_info.id\r\n"
			+ "WHERE display_info.id = :displayInfoId AND reservation_user_comment.id = :commentId";
}
