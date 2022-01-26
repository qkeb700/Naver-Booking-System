package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.CommentDao;
import kr.or.connect.reservation.dao.CommentImageDao;
import kr.or.connect.reservation.dao.DisplayInfoDao;
import kr.or.connect.reservation.dao.DisplayInfoImageDao;
import kr.or.connect.reservation.dao.ProductImageDao;
import kr.or.connect.reservation.dao.ProductPriceDao;
import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.ProductDisplay;
import kr.or.connect.reservation.service.ProductDisplayService;

@Service
public class ProductDisplayServiceImpl implements ProductDisplayService {
	@Autowired
	private DisplayInfoDao displayInfoDao;
	@Autowired
	private ProductImageDao productImageDao;
	@Autowired
	private DisplayInfoImageDao displayInfoImageDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private CommentImageDao commentImageDao;
	@Autowired
	private ProductPriceDao productPriceDao;
	
	public ProductDisplay getProductDisplay(int displayInfoId) {
		ProductDisplay productDisplay = new ProductDisplay();
		productDisplay.setDisplayInfo(displayInfoDao.selectById(displayInfoId));
		productDisplay.setProductImages(productImageDao.selectByDisplayInfoId(displayInfoId));
		productDisplay.setDisplayInfoImage(displayInfoImageDao.selectByDisplayInfoId(displayInfoId));
		List<Comment> comments = commentDao.selectByDisplayInfoId(displayInfoId);
		comments.forEach(item -> {
			List<CommentImage> commentImage = commentImageDao.selectByCommentId(item.getCommentId());
			item.setCommentImages(commentImage);
		});
		productDisplay.setComments(comments);
		productDisplay.setAverageScore(commentDao.selectAvgScoreByDisplayInfoId(displayInfoId));
		productDisplay.setProductPrices(productPriceDao.selectByDisplayInfoId(displayInfoId));
		
		return productDisplay;
	}

}
