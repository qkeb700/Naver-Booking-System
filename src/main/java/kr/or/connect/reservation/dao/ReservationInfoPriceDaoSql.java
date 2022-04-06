package kr.or.connect.reservation.dao;

public class ReservationInfoPriceDaoSql {
	public static final String SELECT_TOTAL_PRICE = "SELECT sum(price*count) FROM reservation_info INNER JOIN reservation_info_price on reservation_info.id = reservation_info_price.reservation_info_id INNER JOIN product_price ON product_price.id = reservation_info_price.product_price_id WHERE reservation_info_id = :reservationInfoId";
	
		public static final String SELECT_PRICES_BY_ID = "SELECT id, reservation_info_id, product_price_id, count "
				+ "FROM reservation_info_price WHERE reservation_info_id = :reservationInfoId";
}
