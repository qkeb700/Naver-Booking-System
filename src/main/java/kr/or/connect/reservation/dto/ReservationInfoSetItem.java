package kr.or.connect.reservation.dto;

public class ReservationInfoSetItem {
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
	private DisplayInfo displayInfo;
	int totalPrice;
	
	public void setReservationInfo(ReservationInfo reservationInfo) {
		reservationInfoId = reservationInfo.getReservationInfoId();
		productId = reservationInfo.getProductId();
		displayInfoId = reservationInfo.getDisplayInfoId();
		reservationName = reservationInfo.getReservationName();
		reservationTel = reservationInfo.getReservationTel();
		cancelFlag = reservationInfo.getCancelFlag();
		reservationDate = reservationInfo.getReservationDate();
		createDate = reservationInfo.getCreateDate();
		modifyDate = reservationInfo.getModifyDate();
		reservationEmail = reservationInfo.getReservationEmail();
	}
}
