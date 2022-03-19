package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dao.ReservationInfoDao;
import kr.or.connect.reservation.dao.ReservationInfoPriceDao;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoPriceDto;
import kr.or.connect.reservation.dto.ReservationInfoSetDto;
import kr.or.connect.reservation.service.ReservationService;

public class ReservationServiceImpl implements ReservationService {
	@Autowired
	private ReservationInfoDao reservationInfoDao;
	@Autowired
	private ReservationInfoPriceDao reservationInfoPriceDao;
	@Autowired
	private DisplayInfoDao displayInfoDao;
	@Override
	
	public ReservationInfoSetDto getReservationInfoSet(String reservationEmail) {
		ReservationInfoSetDto reservationInfoSet = new ReservationInfoSetDto();
		List<ReservationInfo> reservationInfos = reservationInfoDao.selectByEmail(reservationEmail);
		return null;
	}

	@Override
	public ReservationInfoPriceDto addReservation(ReservationInfoPriceDto reservationDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationInfoPriceDto changeCancelFlagById(int reservationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationInfoPriceDto getReservationInfoById(int reservationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existReservation(String reservationEmail) {
		// TODO Auto-generated method stub
		return false;
	}

}
