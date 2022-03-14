package kr.or.connect.reservation.dao;

public class ReservationInfoDaoSql {
	public static final String SELECT_BY_EMAIL = "SELECT id AS reservation_info_id, product_id, display_info_id, reservation_name, reservation_tel, reservation_email, reservation_date, cancel_flag, create_date, modify_date\r\n"
			+ "FROM reservation_info WHERE reservation_email = :reservationEmail";

	public static final String SELECT_TOTAL_PRICE = "SELECT sum(price*count) FROM reservation_info LEFT JOIN reservation_info_price on reservation_info.id = reservation_info_price.reservation_info_id LEFT JOIN product_price ON\r\n"
			+ "product_price.id = reservation_info_price.product_price_id WHERE reservation_email = :reservationEmail AND reservation_info.product_id = :productId AND display_info_id = :displayInfoId";

	public static final String SELECT_ID_BY_EMAIL = "SELECT id FROM reservation_info WHERE reservation_email = :reservationEmail";
	
	public static final String UPDATE_CANCEL_FLAG_BY_ID = "UPDATE reservation_info SET cancel_flag = :cancelFlag, modify_date = :modifyDate WHERE id = :reservationInfoId";
}
