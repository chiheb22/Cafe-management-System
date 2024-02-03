package com.ck.cafe_back.serviceImpl;

import com.ck.cafe_back.DAO.ProductDAO;
import com.ck.cafe_back.JWT.AuthTokenFilter;
import com.ck.cafe_back.POJO.Category;
import com.ck.cafe_back.POJO.Product;
import com.ck.cafe_back.constents.CafeConst;
import com.ck.cafe_back.service.ProductService;
import com.ck.cafe_back.utils.CafeUtils;
import com.ck.cafe_back.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    AuthTokenFilter authTokenFilter;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if (authTokenFilter.isAdmin()){
                if (validateProductMap(requestMap,false)) {
                    productDAO.save(getProductFromMap(requestMap,false));
                    return CafeUtils.getResponse("Product Added Successfully",HttpStatus.OK);

                }
                    return CafeUtils.getResponse(CafeConst.INVALIDATE_DATA,HttpStatus.BAD_REQUEST);
            }else return  CafeUtils.getResponse(CafeConst.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return new ResponseEntity<>(productDAO.getAllProduct(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if (authTokenFilter.isAdmin()){
                if (validateProductMap(requestMap,true)) {
                    Optional<Product> o = productDAO.findById(Integer.parseInt(requestMap.get("id")));
                    if (!o.isEmpty()){
                        Product product = getProductFromMap(requestMap,true);
                        product.setStatus(o.get().getStatus());
                        productDAO.save(product);
                        return CafeUtils.getResponse("Product updated Successfully",HttpStatus.OK);
                    }else
                        return CafeUtils.getResponse("Product does not exist",HttpStatus.OK);


                }
                return CafeUtils.getResponse(CafeConst.INVALIDATE_DATA,HttpStatus.BAD_REQUEST);
            }else return  CafeUtils.getResponse(CafeConst.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean b) {
    Product product = new Product();
    Category category =new Category();
    category.setId(Integer.parseInt(requestMap.get("categoryId")));
    if (b){
        product.setId(Integer.parseInt(requestMap.get("id")));
    }else {
        product.setStatus("true");
    }
    product.setCategory(category);
    product.setName(requestMap.get("name"));
    product.setDescription(requestMap.get("description"));
    product.setPrice(Integer.parseInt(requestMap.get("price")));
    return product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean b) {
    if (requestMap.containsKey("name")){
        if (requestMap.containsKey("id")&&b) {
            return true;
        }else return !b;
        }
    return false;
    }
}
