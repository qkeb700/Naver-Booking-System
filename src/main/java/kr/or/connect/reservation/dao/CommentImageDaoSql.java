package kr.or.connect.reservation.dao;

public class CommentImageDaoSql {
	public static final String SELECT_BY_DISPLAY_INFO_ID = "SELECT reservation_user_comment_image.id AS imageId, reservation_info.id AS reservationInfoId, reservation_user_comment.id AS reservationUserCommentId,\r\n"
			+ "file_info.id AS fileId, file_info.file_name AS fileName, file_info.save_file_name AS saveFileName, file_info.content_type AS contentType, \r\n"
			+ "file_info.delete_flag AS deleteFlag, file_info.create_date AS createDate, file_info.modify_date AS modifyDate FROM reservation_user_comment_image \r\n"
			+ "LEFT JOIN file_info ON reservation_user_comment_image.file_id = file_info.id LEFT JOIN reservation_info ON reservation_user_comment_image.reservation_info_id =\r\n"
			+ "reservation_info.id LEFT JOIN reservation_user_comment ON reservation_user_comment_image.reservation_user_comment_id =\r\n"
			+ "reservation_user_comment.id LEFT JOIN display_info ON reservation_info.display_info_id = display_info.id\r\n"
			+ "WHERE reservation_user_comment.id = :commentId ORDER BY reservation_user_comment_image.id DESC";
}
