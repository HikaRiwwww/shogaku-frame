package com.throne.service.combine;

import com.throne.entity.dto.MainPageInfoDTO;
import com.throne.entity.dto.Result;

public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDTO> getMainPageInfo();
}
