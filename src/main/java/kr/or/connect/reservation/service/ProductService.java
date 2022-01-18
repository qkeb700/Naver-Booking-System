package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductItems;

public interface ProductService {
	public List<Product> getProducts(int start);	
	public List<Product> getProductsByCategoryId(int categoryId, int start);
	public int getCount();
	public int getCountByCategoryId(int categoryId);
}
