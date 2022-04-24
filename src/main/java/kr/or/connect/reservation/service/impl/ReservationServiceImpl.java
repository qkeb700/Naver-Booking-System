package kr.or.connect.reservation.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dao.FileInfoDao;
import kr.or.connect.reservation.dao.ReservationInfoDao;
import kr.or.connect.reservation.dao.ReservationInfoPriceDao;
import kr.or.connect.reservation.dao.ReservationUserCommentDao;
import kr.or.connect.reservation.dao.ReservationUserCommentImageDao;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoPriceDto;
import kr.or.connect.reservation.dto.ReservationInfoSetDto;
import kr.or.connect.reservation.dto.ReservationInfoSetItem;
import kr.or.connect.reservation.dto.ReservationPrice;
import kr.or.connect.reservation.dto.ReservationUserComment;
import kr.or.connect.reservation.dto.ReservationUserCommentImage;
import kr.or.connect.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	private ReservationInfoDao reservationInfoDao;
	@Autowired
	private ReservationInfoPriceDao reservationInfoPriceDao;
	@Autowired
	private DisplayInfoDao displayInfoDao;
	@Autowired
	private ReservationUserCommentDao reservationUserCommentDao;
	@Autowired
	private ReservationUserCommentImageDao reservationUserCommentImageDao;
	@Autowired
	private FileInfoDao fileInfoDao;
	
	@Override
	public ReservationInfoSetDto getReservationInfoSet(String reservationEmail) {
		ReservationInfoSetDto reservationInfoSet = new ReservationInfoSetDto();
		List<ReservationInfo> reservationInfos = reservationInfoDao.selectByEmail(reservationEmail);
		int totalPrice;
		for(ReservationInfo reservationInfo : reservationInfos) {
			ReservationInfoSetItem reservationInfoSetItem = new ReservationInfoSetItem();
			totalPrice = reservationInfoPriceDao.getTotalPrice(reservationInfo.getReservationInfoId());
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
	@Transactional
	public ReservationInfoPriceDto addReservation(ReservationInfoPriceDto reservationInfoPrice) {
		ReservationInfoPriceDto reservationInfoPriceDto;
		int reservationInfoId = reservationInfoDao.insert(reservationInfoPrice);
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
		ReservationInfoSetDto reservationInfoSet = new ReservationInfoSetDto();
		List<ReservationInfo> infoList = reservationInfoDao.selectByEmail(reservationEmail);
		if(infoList == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	@Transactional
	public int writeReview(int reservationInfoId, String filePath, String comment, String productId, int score) {
		 LocalDateTime localDateTime = LocalDateTime.now();
		 String currentTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		 
		 ReservationUserComment reservationComment = new ReservationUserComment();
		 reservationComment.setReservationInfoId(reservationInfoId);
		 reservationComment.setComment(comment);
		 reservationComment.setProductId(Integer.parseInt(productId));
		 reservationComment.setScore(score);
		 reservationComment.setCreateDate(currentTime);
		 reservationComment.setModifyDate(currentTime);
		 int reservationUserCommentId = reservationUserCommentDao.insert(reservationComment);
		 
		 if(filePath == null || filePath.equals("")) {
			 return reservationUserCommentId;
		 }
		 
		 FileInfo fileInfo = new FileInfo();
		 fileInfo.setFileName(filePath.substring(filePath.lastIndexOf("/") + 1));
		 fileInfo.setSaveFileName(filePath.substring(filePath.indexOf("\\") + 1));
		 fileInfo.setContentType("image/" + filePath.substring(filePath.lastIndexOf(".") + 1));
		 fileInfo.setDeleteFlag(0);
		 fileInfo.setCreateDate(currentTime);
		 fileInfo.setModifyDate(currentTime);
		 int fileInfoId = fileInfoDao.insert(fileInfo);
		 
		 ReservationUserCommentImage reservationUserCommentImage = new ReservationUserCommentImage();
		 reservationUserCommentImage.setFileId(fileInfoId);
		 reservationUserCommentImage.setReservationInfoId(reservationInfoId);
		 reservationUserCommentImage.setReservationUserCommentId(reservationUserCommentId);
		 reservationUserCommentImageDao.insert(reservationUserCommentImage);
		 
		 return reservationUserCommentId;
	}

}
