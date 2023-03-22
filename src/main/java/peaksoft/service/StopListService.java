package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.stopList.StopListResponse;

import java.util.List;

public interface StopListService {
    SimpleResponse saveStopList(StopListRequest stopListRequest);
    List<StopListResponse> getAllStopList();
    StopListResponse getByIdStopList(Long stopListId);
    SimpleResponse deleteStopList(Long stopLisId);
    SimpleResponse updateStopList(Long stopListId,StopListRequest stopListRequest);
}
