package com.throne.controller.admin;

import com.throne.entity.bo.HeadLine;
import com.throne.entity.dto.Result;
import com.throne.service.solo.HeadLineService;
import com.throne.service.solo.ShopCategorySerivce;
import org.shogakuframework.core.annotations.Controller;
import org.shogakuframework.inject.annotations.AutoWired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminPageController {
    @AutoWired
    private HeadLineService headLineService;
    @AutoWired
    private ShopCategorySerivce shopCategorySerivce;

    public Result<Boolean> addHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.addHeadLine(new HeadLine());
    }

    public HeadLineService getHeadLineService() {
        return headLineService;
    }

    public ShopCategorySerivce getShopCategorySerivce() {
        return shopCategorySerivce;
    }

    public Result<Boolean> removeHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        return headLineService.removeHeadLine(headLine);
    }

    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.modifyHeadLine(new HeadLine());
    }


}
