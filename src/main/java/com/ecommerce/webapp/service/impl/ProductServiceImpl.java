package com.ecommerce.webapp.service.impl;

import com.ecommerce.webapp.model.Product;
import com.ecommerce.webapp.repository.ProductRepository;
import com.ecommerce.webapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        return null;
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
         Optional<Product> product = productRepository.findById(id);
        return product;
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) {

        Optional<Product> dbProductOtional = getProductById(product.getId());
        Product dbProduct = dbProductOtional.get();
        String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();

        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setStock(product.getStock());
        dbProduct.setImage(imageName);
        dbProduct.setIsActive(product.getIsActive());
        dbProduct.setDiscount(product.getDiscount());

        // 5=100*(5/100); 100-5=95
        Double disocunt = product.getPrice() * (product.getDiscount() / 100.0);
        Double discountPrice = product.getPrice() - disocunt;
        dbProduct.setDiscountPrice(discountPrice);

        Product updateProduct = productRepository.save(dbProduct);

        if (!ObjectUtils.isEmpty(updateProduct)) {

            if (!image.isEmpty()) {

                try {
                    File saveFile = new ClassPathResource("static/img").getFile();

                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
                            + image.getOriginalFilename());
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return product;
        }
        return null;
    }


    @Override
    public List<Product> getAllActiveProducts(String category) {
        if(ObjectUtils.isEmpty(category)){
            return productRepository.findAll();
        }
       return  productRepository.findByCategory(category);
    }

    @Override
    public List<Product> searchProduct(String ch) {
        return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch);
    }

    @Override
    public Page<Product> getAllActiveProductPagination(Integer pageNo, Integer pageSize, String category) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> pageProduct = null;

        if (ObjectUtils.isEmpty(category)) {
            pageProduct = productRepository.findByIsActiveTrue(pageable);
        } else {
            pageProduct = productRepository.findByCategory(pageable, category);
        }
        return pageProduct;
    }

    @Override
    public Page<Product> searchProductPagination(Integer pageNo, Integer pageSize, String ch) {
        return null;
    }

    @Override
    public Page<Product> getAllProductsPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String ch) {

        Page<Product> pageProduct = null;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        pageProduct = productRepository.findByisActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch,
                ch, pageable);

//		if (ObjectUtils.isEmpty(category)) {
//			pageProduct = productRepository.findByIsActiveTrue(pageable);
//		} else {
//			pageProduct = productRepository.findByCategory(pageable, category);
//		}
        return pageProduct;
    }
}
