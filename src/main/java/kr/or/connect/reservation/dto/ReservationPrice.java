package kr.or.connect.reservation.dto;

public class ReservationPrice {
	public int reservationInfoPriceId;
	public int reservationInfoId;
	public int productPriceId;
	public int count;
	
	
	public int getReservationInfoPriceId() {
		return reservationInfoPriceId;
	}
	public void setReservationInfoPriceId(int reservationInfoPriceId) {
		this.reservationInfoPriceId = reservationInfoPriceId;
	}
	public int getReservationInfoId() {
		return reservationInfoId;
	}
	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}
	public int getProductPriceId() {
		return productPriceId;
	}
	public void setProductPriceId(int productPriceId) {
		this.productPriceId = productPriceId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "ReservationInfoPrice [id=" + reservationInfoPriceId + ", reservationInfoId=" + reservationInfoId + ", productPriceId="
				+ productPriceId + ", count=" + count + "]";
	}
	
	
}
