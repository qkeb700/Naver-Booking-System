package kr.or.connect.reservation.dao;

public class ReservationInfoDaoSql {
	public static final String SELECT_BY_EMAIL = "SELECT id AS reservation_info_id, product_id, display_info_id, reservation_name, reservation_tel, reservation_email, reservation_date, cancel_flag, create_date, modify_date\r\n"
			+ "FROM reservation_info WHERE reservation_email = :reservationEmail";

	public static final String SELECT_TOTAL_PRICE = "";
}
