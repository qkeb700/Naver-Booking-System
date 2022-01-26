package kr.or.connect.reservation.dao;

public class CommentDaoSql {
	public static final String SELECT_BY_DISPLAY_INFO_ID = "SELECT reservation_user_comment.id AS commentId, product.id AS productId, reservation_info.id AS reservationInfoId, reservation_user_comment.score,\r\n"
			+ "reservation_user_comment.comment, reservation_info.reservation_name AS reservationName, reservation_info.reservation_tel AS\r\n"
			+ "reservationTelephone, reservation_info.reservation_email AS reservationEmail, reservation_info.reservation_date AS reservationDate,\r\n"
			+ "reservation_user_comment.create_date AS createDate, reservation_user_comment.modify_date AS modifyDate FROM reservation_user_comment INNER JOIN product ON\r\n"
			+ "reservation_user_comment.product_id = product.id INNER JOIN reservation_info ON \r\n"
			+ "reservation_user_comment.reservation_info_id = reservation_info.id INNER JOIN display_info \r\n"
			+ "ON reservation_info.display_info_id = display_info.id WHERE display_info.id = :displayInfoId ORDER BY reservation_user_comment.id DESC";
	
	public static final String SELECT_AVG_SCORE_BY_DISPLAY_INFO_ID = "SELECT AVG(score) FROM reservation_user_comment LEFT JOIN product ON reservation_user_comment.product_id = \r\n"
			+ "product.id LEFT JOIN display_info ON product.id = display_info.product_id WHERE display_info.id = :displayInfoId";
}
