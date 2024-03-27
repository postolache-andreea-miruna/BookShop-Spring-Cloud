package com.awbd.bookshop.services.basket;

import com.awbd.bookshop.dtos.BasketDetails;
import com.awbd.bookshop.dtos.BookFromBasketDetails;
import com.awbd.bookshop.models.Basket;
import com.awbd.bookshop.models.User;
import com.awbd.bookshop.repositories.BasketRepository;
import com.awbd.bookshop.services.bookbasket.IBookBasketService;
import com.awbd.bookshop.services.user.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class BasketService implements IBasketService {
    private final BasketRepository basketRepository;
    private final IBookBasketService bookBasketService;
    private final IUserService userService;

    public BasketService(
            BasketRepository basketRepository,
            IBookBasketService bookBasketService,
            IUserService userService) {
        this.basketRepository = basketRepository;
        this.bookBasketService = bookBasketService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Basket createBasket(int userId) {
        User user = userService.getUser(userId);

        Basket basket = basketRepository.findByUserId(userId).orElse(null);

        if (basket == null) {
            return basketRepository.save(new Basket(
                    0,
                    false,
                    0,
                    user
            ));
        }

        return basket;
    }

    @Transactional
    @Override
    public Basket sentOrder(int userId) {
        Basket basket = basketRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("User does not have a current basket"));

        if (basket.getCost() == 0)
            throw new NoSuchElementException("User does not have books in basket");

        basket.setSent(true);
        return basketRepository.save(basket);
    }

    @Transactional
    @Override
    public Basket getBasket(int userId) {
        Basket basket = basketRepository.findByUserId(userId).orElse(null);

        if (basket == null)
            basket = basketRepository.save(new Basket(
                    0,
                    false,
                    0,
                    userService.getUser(userId)
            ));

        return basket;
//
//        return new BasketDetails(
//                basket.getId(),
//                basket.getSent().toString(),
//                basket.getUser().getId(),
//                basket.getUser().getEmail(),
//                basket.getCost(),
//                basketRepository.findBooksFromCurrentBasket(basket.getId())
//        );
    }

    @Transactional
    @Override
    public List<BookFromBasketDetails> findBooksFromCurrentBasket(int basketId) {
        return basketRepository.findBooksFromCurrentBasket(basketId);
    }

    @Transactional
    @Override
    public Basket addBookToBasket(int bookId, int basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new NoSuchElementException("Does not exist a basket with this id")
        );

        Integer bookPriceInBasket = bookBasketService.addBookToBasket(bookId, basket);

        basket.setCost(basket.getCost() + bookPriceInBasket);
        return basketRepository.save(basket);
    }

    @Transactional
    @Override
    public Basket removeBookFromBasket(int bookId, int basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new NoSuchElementException("Does not exist a basket with this id"));

        Integer bookPriceInBasket = bookBasketService.removeBookToBasket(bookId, basketId);

        basket.setCost(basket.getCost() - bookPriceInBasket);
        return basketRepository.save(basket);
    }

    @Transactional
    @Override
    public Basket decrementBookFromBasket(int bookId, int basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new NoSuchElementException("Does not exist a basket with this id"));

        Integer bookPriceInBasket = bookBasketService.decrementBookFromBasket(bookId, basketId);

        basket.setCost(basket.getCost() - bookPriceInBasket);
        return basketRepository.save(basket);
    }
}