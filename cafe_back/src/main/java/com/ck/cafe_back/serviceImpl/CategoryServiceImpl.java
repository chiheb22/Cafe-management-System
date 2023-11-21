package com.ck.cafe_back.serviceImpl;

import com.ck.cafe_back.DAO.CategoryDAO;
import com.ck.cafe_back.JWT.AuthTokenFilter;
import com.ck.cafe_back.POJO.Category;
import com.ck.cafe_back.constents.CafeConst;
import com.ck.cafe_back.service.CategoryService;
import com.ck.cafe_back.utils.CafeUtils;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    AuthTokenFilter authTokenFilter;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (authTokenFilter.isAdmin()){
                if (validateCategoryMap(requestMap,false)){
                    categoryDAO.save(getCategoryFromMap(requestMap,false));
                    return CafeUtils.getResponse("Category Added Successfully",HttpStatus.OK);
                }
            }else
                return CafeUtils.getResponse(CafeConst.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue)&&filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<Category>>(categoryDAO.getAllCategories(),HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDAO.findAll(),HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (authTokenFilter.isAdmin()){
                if (validateCategoryMap(requestMap,true)){
                    Optional<Category> optional  = categoryDAO.findById(Integer.parseInt(requestMap.get("id")));
               if (optional.isPresent()){
                   categoryDAO.save(getCategoryFromMap(requestMap,true));
                   return CafeUtils.getResponse("Category updated Successfully",HttpStatus.OK);
               }else
                   return CafeUtils.getResponse("Category id does not exist", HttpStatus.OK);
                }
                return CafeUtils.getResponse(CafeConst.INVALIDATE_DATA,HttpStatus.OK);

        }else return CafeUtils.getResponse(CafeConst.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateCategoryMap(Map<String, String> requestMap, boolean b) {
    if (requestMap.containsKey("name")){
        if (requestMap.containsKey("id")&& b) {
            return true;
        }else return !b;

        }
    return false;
    }
    private Category getCategoryFromMap(Map<String,String> requestMap , Boolean isAddd){
        Category c = new Category();
        if (isAddd){
            c.setId(Integer.parseInt(requestMap.get("id")));
        }
        c.setName(requestMap.get("name"));
        return c;
    }

}
