package com.throne.service.solo;

import com.throne.entity.bo.HeadLine;
import com.throne.entity.dto.Result;

import java.util.List;

public interface HeadLineService {
    Result<Boolean> addHeadLine(HeadLine headLine);

    Result<Boolean> removeHeadLine(HeadLine headLine);

    Result<Boolean> modifyHeadLine(HeadLine headLine);

    Result<HeadLine> getHeadLineById(Integer headLineId);

    Result<List<HeadLine>> getHeadLineList(HeadLine headLine, Integer pageIndex, Integer pageSize);
}
