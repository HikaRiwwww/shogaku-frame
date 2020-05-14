package com.throne.service.solo.impl;

import com.throne.entity.bo.ShopCategory;
import com.throne.entity.dto.Result;
import com.throne.service.solo.ShopCategorySerivce;
import org.shogakuframework.core.annotations.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategorySerivce {
    @Override
    public Result<Boolean> addShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<Boolean> removeShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<Boolean> modifyShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<ShopCategory> getShopCategoryById(Integer shopCategoryId) {
        return null;
    }

    @Override
    public Result<List<ShopCategory>> getShopCategoryList(ShopCategory shopCategory, Integer pageIndex, Integer pageSize) {
        return null;
    }
}
