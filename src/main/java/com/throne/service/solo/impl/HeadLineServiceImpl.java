package com.throne.service.solo.impl;

import com.throne.entity.bo.HeadLine;
import com.throne.entity.dto.Result;
import com.throne.service.solo.HeadLineService;
import org.shogakuframework.core.annotations.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Override
    public Result<Boolean> addHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<Boolean> removeHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> getHeadLineById(Integer headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> getHeadLineList(HeadLine headLine, Integer pageIndex, Integer pageSize) {
        return null;
    }
}
