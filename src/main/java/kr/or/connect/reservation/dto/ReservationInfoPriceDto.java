package kr.or.connect.reservation.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationInfoPriceDto {
	private int reservationInfoId;
	private int productId;
	private int displayInfoId;
	private String reservationName;
	private String reservationTel;
	private String reservationEmail;
	private String reservationDate;
	private int cancelFlag;
	private String createDate;
	private String modifyDate;
	private List<ReservationPrice> prices;
	
	public ReservationInfoPriceDto() {
		prices = new ArrayList<ReservationPrice>();
		prices = null;
		cancelFlag = 0;
		createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		modifyDate = createDate;
	}
	public void setReservationInfo(ReservationInfo reservationInfo) {
		reservationInfoId = reservationInfo.getReservationInfoId();
		productId = reservationInfo.getProductId();
		displayInfoId = reservationInfo.getDisplayInfoId();
		reservationName = reservationInfo.getReservationName();
		reservationTel = reservationInfo.getReservationTel();
		cancelFlag = reservationInfo.getCancelFlag();
		reservationDate= reservationInfo.getReservationDate();
		createDate = reservationInfo.getCreateDate();
		modifyDate = reservationInfo.getModifyDate();
		reservationEmail = reservationInfo.getReservationEmail();
	}
	public void setReservationInfoById(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
		for(ReservationPrice price : this.prices) {
			price.setReservationInfoId(reservationInfoId);
		}
	}
}
