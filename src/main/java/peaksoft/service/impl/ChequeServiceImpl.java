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
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

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
    public SimpleResponse saveCheque(Long menuItemId, ChequeRequest chequeRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NoSuchElementException(String.format("MenuItem with id: %s not found",menuItemId)));


             User user=(userRepository.findById(chequeRequest.userId())
                        .orElseThrow(() -> new NoSuchElementException(String.format("User with id: %s not found", chequeRequest.userId()))));
        Cheque cheque = new Cheque();
        cheque.setCreatedAt(LocalDate.now());
        cheque.setPriceAverage(menuItem.getPrice().intValue());
        cheque.setUser(user);
        menuItem.addCheque(cheque);
        chequeRepository.save(cheque);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Cheque with id: %s successfully saved", cheque.getId())).build();
    }

    @Override
    public ChequeResponse getByIdCheque(Long chequeId) {
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(()->new NoSuchElementException(String.format("Cheque with id:%s not found",chequeId)));
        User user = userRepository.findById(cheque.getUser().getId()).orElseThrow();
        int summa = 0;
        for (MenuItem menuItem : cheque.getMenuItems()) {
            summa += menuItem.getPrice().intValue();
        }
        Restaurant restaurant = repository.findById(cheque.getUser().getRestaurant().getId()).orElseThrow();
        int service = summa * restaurant.getService() / 100;
        BigDecimal grandTotal = BigDecimal.valueOf(summa + service);
        ChequeResponse response = new ChequeResponse();
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setMenuItems(cheque.getMenuItems());
        response.setAveragePrice(summa);
        response.setService(service);
        response.setGrandTotal(grandTotal);
        return response;
    }

    @Override
    public SimpleResponse deleteCheque(Long chequeId) {
        chequeRepository.deleteById(chequeId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Cheque with id: %s successfully updated", chequeId)).build();
    }

    @Override
    public SimpleResponse updateCheque(Long chequeId, ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(()->new NoSuchElementException(String.format("The cheque with this id: %s not found",chequeId)));
        cheque.setCreatedAt(chequeRequest.createdAt());
        chequeRepository.save(cheque);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Cheque with id: %s successfully updated", cheque.getId())).build();
    }


    @Override
    public ChequeResponseSumPerDay countWaiterCheque(Long userId) {
        List<MenuItemForCheque> cheques = menuItemRepository.findUserIdChequeDate(userId);
        int summa = 0;
        for (MenuItemForCheque cheque : cheques) {
            summa += cheque.price().intValue();
        }
        ChequeResponseSumPerDay response = new ChequeResponseSumPerDay();
        User user = userRepository.findById(userId).orElseThrow();
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setAverage(summa);
        return response;

    }

    @Override
    public ChequeRestaurantAverage averageChequeRestaurant() {
        Restaurant restaurant = repository.findById(1L).orElseThrow();

        int summa = 0;
        int count = 0;
        for (User user : restaurant.getUsers()) {
            for (Cheque cheque : user.getCheques()) {
                var day = LocalDate.now().getDayOfMonth() - cheque.getCreatedAt().getDayOfMonth();
                if (day == 1) {
                    count++;
                    summa += cheque.getPriceAverage();
                }
            }
        }
        int averageSum = summa / count;
        ChequeRestaurantAverage restaurantAverage = new ChequeRestaurantAverage();
        restaurantAverage.setName(restaurant.getName());
        restaurantAverage.setTotalGrand(averageSum);

        return restaurantAverage;
    }
}
