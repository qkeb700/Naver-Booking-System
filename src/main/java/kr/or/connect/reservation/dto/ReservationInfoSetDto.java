package kr.or.connect.reservation.dto;

import java.util.ArrayList;
import java.util.List;

public class ReservationInfoSetDto {
	public List<ReservationInfoSetItem> reservations;
	public int size;
	
	public ReservationInfoSetDto() {
		this.reservations = new ArrayList<ReservationInfoSetItem>();
		this.size = 0;
	}
	public void addReservationItem(ReservationInfoSetItem reservationItem) {
		this.reservations.add(reservationItem);
	}
	public void addSize(int count) {
		this.size += count;
	}
	public void setReservations(List<ReservationInfoSetItem> reservations) {
		if(reservations == null) {
			this.reservations = null;
		} else {
			this.reservations.addAll(reservations);
		}
	}
}
