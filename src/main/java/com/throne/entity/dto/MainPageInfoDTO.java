package com.throne.entity.dto;

import com.throne.entity.bo.HeadLine;
import com.throne.entity.bo.ShopCategory;
import org.shogakuframework.core.annotations.Repository;

import java.util.List;

public class MainPageInfoDTO {
    private List<HeadLine> headLineList;
    private List<ShopCategory> shopCategoryList;
}
