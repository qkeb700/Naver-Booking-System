package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dao.ReservationInfoDao;
import kr.or.connect.reservation.dao.ReservationInfoPriceDao;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoPriceDto;
import kr.or.connect.reservation.dto.ReservationInfoSetDto;
import kr.or.connect.reservation.dto.ReservationInfoSetItem;
import kr.or.connect.reservation.dto.ReservationPrice;
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
		int totalPrice;
		for(ReservationInfo reservationInfo : reservationInfos) {
			ReservationInfoSetItem reservationInfoSetItem = new ReservationInfoSetItem();
			totalPrice = reservationInfoDao.selectTotalPrice(reservationEmail, reservationInfo.getProductId(), reservationInfo.getDisplayInfoId());
			DisplayInfo displayInfo = displayInfoDao.selectById(reservationInfo.getDisplayInfoId());
			reservationInfoSetItem.setReservationInfo(reservationInfo);
			reservationInfoSetItem.setDisplayInfo(displayInfo);
			reservationInfoSetItem.setTotalPrice(totalPrice);
			
			reservationInfoSet.addReservationItem(reservationInfoSetItem);
			reservationInfoSet.addSize(1);
		}
		return reservationInfoSet;
	}

	@Override
	public ReservationInfoPriceDto addReservation(ReservationInfoPriceDto reservationInfoPrice) {
		ReservationInfoPriceDto reservationInfoPriceDto;
		ReservationInfo reservationInfo = new ReservationInfo();
		int reservationInfoId = reservationInfoDao.insert(reservationInfo);
		reservationInfoPrice.setReservationInfoById(reservationInfoId);
		
		for(ReservationPrice reservationPrice : reservationInfoPrice.getPrices()) {
			if(reservationPrice.getCount() > 0) {
				reservationInfoPriceDao.insert(reservationPrice);
			}
		}
		reservationInfoPriceDto = getReservationInfoById(reservationInfoId);
		return reservationInfoPriceDto;
	}

	@Override
	public ReservationInfoPriceDto changeCancelFlagById(int reservationId) {
		reservationInfoDao.updateCancelFlagById(reservationId, 1);
		ReservationInfoPriceDto updatedReservation = getReservationInfoById(reservationId);
		return updatedReservation;
	}

	@Override
	public ReservationInfoPriceDto getReservationInfoById(int reservationId) {
		ReservationInfoPriceDto reservationInfoPrice = new ReservationInfoPriceDto();
		ReservationInfo reservationInfo = reservationInfoDao.getReservationInfo(reservationId);
		List<ReservationPrice> prices = reservationInfoPriceDao.getPriceList(reservationId);
		
		reservationInfoPrice.setReservationInfo(reservationInfo);
		reservationInfoPrice.setPrices(prices);
		return reservationInfoPrice;
	}

	@Override
	public boolean existReservation(String reservationEmail) {
		
		return false;
	}

}
