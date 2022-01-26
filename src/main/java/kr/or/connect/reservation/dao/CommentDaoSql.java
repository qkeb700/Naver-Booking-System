package kr.or.connect.reservation.dao;

public class CommentDaoSql {
	public static final String SELECT_BY_DISPLAY_INFO_ID = "SELECT reservation_user_comment.comment, reservation_user_comment.id AS commentId, reservation_user_comment.create_date AS createDate, reservation_user_comment.modify_date AS modifyDate, \r\n"
			+ "product.id AS productId, reservation_info.reservation_date AS reservationDate, \r\n"
			+ "reservation_info.reservation_email AS reservationEmail, reservation_info.id AS reservationInfoId, \r\n"
			+ "reservation_info.reservation_name AS reservationName, reservation_info.reservation_tel AS\r\n"
			+ "reservationTelephone, reservation_user_comment.score FROM reservation_user_comment LEFT JOIN product ON\r\n"
			+ "reservation_user_comment.product_id = product.id LEFT JOIN reservation_info ON \r\n"
			+ "reservation_user_comment.reservation_info_id = reservation_info.id LEFT JOIN display_info \r\n"
			+ "ON reservation_info.display_info_id = display_info.id WHERE display_info.id = :displayInfoId";
	
	public static final String SELECT_AVG_SCORE_BY_DISPLAY_INFO_ID = "SELECT AVG(score) FROM reservation_user_comment LEFT JOIN product ON reservation_user_comment.product_id = \r\n"
			+ "product.id LEFT JOIN display_info ON product.id = display_info.product_id WHERE display_info.id = :displayInfoId";
}
