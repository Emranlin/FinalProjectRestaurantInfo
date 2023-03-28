package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.cheque.ChequeResponse;
import peaksoft.dto.response.cheque.ChequeResponseSumPerDay;
import peaksoft.dto.response.cheque.ChequeRestaurantAverage;
import peaksoft.dto.response.menuItem.MenuItemForCheque;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Transactional
@Service
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository repository;

    public ChequeServiceImpl(ChequeRepository chequeRepository, UserRepository userRepository, MenuItemRepository menuItemRepository, RestaurantRepository repository) {
        this.chequeRepository = chequeRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.repository = repository;
    }

    @Override
    public SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest) {
        List<MenuItem> menuItems = new ArrayList<>();
        Cheque cheque = new Cheque();
        cheque.setCreatedAt(LocalDate.now());
        for (Long mealId : chequeRequest.menuItemId()) {
            MenuItem menuItem = menuItemRepository.findById(mealId).orElseThrow(
                    () -> new NotFoundException(String.format("Meal with id: %s  is not found!", mealId)));
            menuItems.add(menuItem);
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("User with id: %s not found", userId)));
        cheque.setUser(user);
        int summa=0;
        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
            summa += menuItem.getPrice().intValue();
        }
        cheque.setPriceAverage(summa);
        chequeRepository.save(cheque);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Cheque with id: %s successfully saved", cheque.getId())).build();
    }

    @Override
    public ChequeResponse getByIdCheque(Long chequeId) {
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(() -> new NotFoundException(String.format("Cheque with id:%s not found", chequeId)));
        User user = userRepository.findById(cheque.getUser().getId()).orElseThrow(()->new NotFoundException("User with id not found"));
        int summa = 0;
        for (MenuItem menuItem : cheque.getMenuItems()) {
            summa += menuItem.getPrice().intValue();
        }
        Restaurant restaurant = repository.findById(cheque.getUser().getRestaurant().getId()).orElseThrow();
        int service = summa * restaurant.getService() / 100;
        BigDecimal grandTotal = BigDecimal.valueOf(summa + service);
        ChequeResponse response = new ChequeResponse();
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setMeals(chequeRepository.getMenuItem(chequeId));
        response.setAveragePrice(summa);
        response.setService(service);
        response.setGrandTotal(grandTotal);
        return response;
    }

    @Override
    public SimpleResponse deleteCheque(Long chequeId) {
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(() -> new NotFoundException(String.format("Cheque with id :%s not found", chequeId)));
        cheque.getMenuItems().forEach(menuItem -> menuItem.getCheques().remove(cheque));
        chequeRepository.deleteById(chequeId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Cheque with id: %s successfully updated", chequeId)).build();
    }

    @Override
    public SimpleResponse updateCheque(Long chequeId, ChequeRequest chequeRequest) {
        List<MenuItem> menuItems;
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(
                () -> new NotFoundException(String.format("Cheque with id :%s is not found!", chequeId)));
        menuItems = chequeRequest.menuItemId().stream().map(mealId -> menuItemRepository.findById(mealId).orElseThrow(
                () -> new NotFoundException(String.format("Meal with id:%s is not found!", mealId)))).collect(Collectors.toList());

        for (MenuItem menuItem : cheque.getMenuItems()) {
            menuItem.getCheques().remove(cheque);
        }
        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Your check has been updated!")
                .build();
    }


    @Override
    public ChequeResponseSumPerDay countWaiterCheque(Long userId, LocalDate date) {
        List<Cheque> cheques = chequeRepository.findByUser_IdAndCreatedAt(userId, date);
        int summa = 0;
        for (Cheque cheque : cheques) {
            for (MenuItem menuItem : cheque.getMenuItems()) {
                summa += menuItem.getPrice().intValue();
            }
        }
        ChequeResponseSumPerDay response = new ChequeResponseSumPerDay();
        User user = userRepository.findById(userId).orElseThrow();
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setAverage(summa);
        return response;

    }

    @Override
    public SimpleResponse averageChequeRestaurant(LocalDate date) {
        Integer avg = chequeRepository.avg(date);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Average check  of date: %s and total summa : %s", date, avg)).build();
    }


}



