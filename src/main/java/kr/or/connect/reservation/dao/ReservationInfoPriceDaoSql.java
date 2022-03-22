package kr.or.connect.reservation.dao;

public class ReservationInfoPriceDaoSql {
	public static final String SELECT_PRICES_BY_ID = "SELECT reservation_info_price_id, reservation_info_id, product_price_id, count "
			+ "FROM reservation_info_price WHERE reservation_info_id = :reservationInfoId";
}
