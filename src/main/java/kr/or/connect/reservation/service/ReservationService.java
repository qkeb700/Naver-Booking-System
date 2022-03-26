package kr.or.connect.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dao.ReservationInfoDao;
import kr.or.connect.reservation.dao.ReservationInfoPriceDao;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoPriceDto;
import kr.or.connect.reservation.dto.ReservationInfoSetDto;

public interface ReservationService {
	public ReservationInfoSetDto getReservationInfoSet(String reservationEmail);
	public ReservationInfoPriceDto addReservation(ReservationInfoPriceDto reservationDto);
	public ReservationInfoPriceDto changeCancelFlagById(int reservationId);
	public ReservationInfoPriceDto getReservationInfoById(int reservationId);
	public boolean existReservation(String reservationEmail);
}
