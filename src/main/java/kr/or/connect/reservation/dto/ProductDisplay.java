package kr.or.connect.reservation.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDisplay {
	private DisplayInfo displayInfo;
	private List<ProductImage> productImages;
	private DisplayInfoImage displayInfoImage;
	private List<Comment> comments;
	private double averageScore;
	private List<ProductPrice> productPrices;
	
	public ProductDisplay() {
		this.productImages = new ArrayList<ProductImage>();
		this.comments = new ArrayList<Comment>();
		this.productPrices = new ArrayList<ProductPrice>();
	}
	public void setProductImages(List<ProductImage> productImages) {
		if(productImages.isEmpty()) {
			this.productImages = null;
		} else {
			this.productImages.addAll(productImages);
		}
	}
	public void setComments(List<Comment> comments) {
		if(comments.isEmpty()) {
			this.comments = null;
		} else {
			this.comments.addAll(comments);
		}
	}
	public DisplayInfoImage getDisplayInfoImage() {
		return displayInfoImage;
	}
	public void setDisplayInfoImage(DisplayInfoImage displayInfoImage) {
		this.displayInfoImage = displayInfoImage;
	}
	public double getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public List<ProductPrice> getProductPrices() {
		return productPrices;
	}
	public void setProductPrices(List<ProductPrice> productPrices) {
		if(productPrices.isEmpty()) {
			this.productPrices = null;
		} else {
			this.productPrices.addAll(productPrices);
		}
	}
	public DisplayInfo getDisplayInfo() {
		return displayInfo;
	}
	public void setDisplayInfo(DisplayInfo displayInfo) {
		this.displayInfo = displayInfo;
	}
}
