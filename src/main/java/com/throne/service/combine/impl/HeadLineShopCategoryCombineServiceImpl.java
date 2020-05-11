package com.throne.service.combine.impl;

import com.throne.entity.bo.HeadLine;
import com.throne.entity.bo.ShopCategory;
import com.throne.entity.dto.MainPageInfoDTO;
import com.throne.entity.dto.Result;
import com.throne.service.combine.HeadLineShopCategoryCombineService;
import com.throne.service.solo.HeadLineService;
import com.throne.service.solo.ShopCategorySerivce;

import java.util.List;

public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {

    private HeadLineService headLineService;
    private ShopCategorySerivce shopCategorySerivce;

    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        Result<List<HeadLine>> headLineList = headLineService.getHeadLineList(headLineCondition, 1, 4);
        ShopCategory shopCategoryCondition = new ShopCategory();
        Result<List<ShopCategory>> shopCategoryList = shopCategorySerivce.getShopCategoryList(shopCategoryCondition, 1, 10);

        return mergeHeadLineAndShopCategory(headLineList, shopCategoryList);

    }

    private Result<MainPageInfoDTO> mergeHeadLineAndShopCategory(Result<List<HeadLine>> headLineList, Result<List<ShopCategory>> shopCategoryList) {
        return null;
    }
}
