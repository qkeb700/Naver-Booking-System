package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductItems;
import kr.or.connect.reservation.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;
	
	@Override
	@Transactional
	public List<Product> getProducts(int start) {
		return productDao.selectAll(start);
	}

	@Override
	@Transactional
	public List<Product> getProductsByCategoryId(int categoryId, int start) {
		return productDao.selectAllByCategoryId(categoryId, start);
	}

	@Override
	@Transactional
	public int getCount() {
		return productDao.getCount();
	}

	@Override
	@Transactional
	public int getCountByCategoryId(int categoryId) {
		return productDao.getCountByCategoryId(categoryId);
	}

}
