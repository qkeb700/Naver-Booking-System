package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.ReservationInfo;

public interface ReservationService {
	public List<ReservationInfo> getReservationInfoByEmail(String reservationEmail);
	
	public int getTotalPrice(String reservationEmail, int productId, int displayInfoId, String reservationDate);

	public int getIdByEmail(String reservationEmail);
}
