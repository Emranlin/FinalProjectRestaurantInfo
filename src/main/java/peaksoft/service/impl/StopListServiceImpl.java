package peaksoft.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.stopList.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class StopListServiceImpl implements StopListService {
    private final StopListRepository repository;
    private final MenuItemRepository menuItemRepository;

    public StopListServiceImpl(StopListRepository repository, MenuItemRepository menuItemRepository) {
        this.repository = repository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse saveStopList(StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(stopListRequest.menuItemId())
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("MenuItem with id: %s not found", stopListRequest.menuItemId())));
        boolean exists = repository.existsByDateAndMenuItemName(stopListRequest.date(), menuItem.getName());
        if (exists){
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("The MenuItem named %s has already been added to the stop list on this date",menuItem.getName()))
                    .build();
        }

        StopList stopList=new StopList();
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        stopList.setMenuItem(menuItem);
        menuItem.setStopList(stopList);
        repository.save(stopList);
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("StopList with id:%s successfully saved",stopList.getId())).build();
    }

    @Override
    public List<StopListResponse> getAllStopList() {
        return repository.getAllStopList();
    }

    @Override
    public StopListResponse getByIdStopList(Long stopListId) {
        StopList stopList = repository.findById(stopListId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("StopList with id: %s not found",stopListId)));

        return repository.getStopListById(stopListId);
    }

    @Override
    public SimpleResponse deleteStopList(Long stopListId) {
        StopList stopList = repository.findById(stopListId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("StopList with id: %s not found!",stopListId)));
        repository.deleteById(stopListId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("StopList with id:%s successfully deleted",stopListId)).build();
    }

    @Override
    public SimpleResponse updateStopList(Long stopListId, StopListRequest stopListRequest) {
        StopList stopList = repository.findById(stopListId).orElseThrow(() -> new NoSuchElementException(   String.format("StopList with id: %s not found",stopListId)));
        boolean exists = repository.existsByDateAndMenuItemNameAndDate( stopListRequest.date(),stopList.getMenuItem().getName(), stopListId);
        if (exists){
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("The MenuItem named %s has already has  stop list on this date",stopList.getMenuItem().getName()))
                    .build();
        }
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        repository.save(stopList);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("StopList with id:%s successfully updated",stopListId)).build();
    }
}
