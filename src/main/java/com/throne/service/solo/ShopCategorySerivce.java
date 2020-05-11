package com.throne.service.solo;

import com.throne.entity.bo.ShopCategory;
import com.throne.entity.dto.Result;

import java.util.List;

public interface ShopCategorySerivce {
    Result<Boolean> addShopCategory(ShopCategory shopCategory);

    Result<Boolean> removeShopCategory(ShopCategory shopCategory);

    Result<Boolean> modifyShopCategory(ShopCategory shopCategory);

    Result<ShopCategory> getShopCategoryById(Integer shopCategoryId);

    Result<List<ShopCategory>> getShopCategoryList(ShopCategory shopCategory, Integer pageIndex, Integer pageSize);

}
