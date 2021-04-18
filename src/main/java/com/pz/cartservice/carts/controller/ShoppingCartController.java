package com.pz.cartservice.carts.controller;

import com.pz.cartservice.carts.dto.ShoppingCartGetDTO;
import com.pz.cartservice.carts.dto.ShoppingCartItemGetDTO;
import com.pz.cartservice.carts.dto.ShoppingCartItemPostDTO;
import com.pz.cartservice.carts.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// TODO: better api design
@RestController
@RequestMapping("/carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;


    public ShoppingCartController(ShoppingCartService shoppingCartService){
        this.shoppingCartService = shoppingCartService;
    }


    @GetMapping
    public Long getEmptyCart() {
        return shoppingCartService.createEmptyShoppingCart();
    }


    @GetMapping("/{cart_id}")
    public ShoppingCartGetDTO getCart(@PathVariable("cart_id") Long cartId) {
        return shoppingCartService.getExistingShoppingCart(cartId);
    }


    @GetMapping("/{cart_id}/{item_id}")
    public ShoppingCartItemGetDTO getItemFromCart(@PathVariable("cart_id") Long cartId, @PathVariable("item_id") Long itemId) {
        return shoppingCartService.getItemFromShoppingCart(cartId, itemId);
    }


    @PostMapping("/{cart_id}")
    public Long addItemToCart(@PathVariable("cart_id") Long cartId, @RequestBody @Valid ShoppingCartItemPostDTO item) {
        return shoppingCartService.addItemToCart(cartId, item);
    }


    @PostMapping("/{cart_id}/{item_id}")
    public Long editItemInCart(@PathVariable("cart_id") Long cartId, @PathVariable("item_id") Long itemId, @RequestBody @Valid ShoppingCartItemPostDTO item) {
        return shoppingCartService.editItemInCart(cartId, itemId, item);
    }


    @DeleteMapping("/{cart_id}/{item_id}")
    public Long removeFromCart(@PathVariable("cart_id") Long cartId, @PathVariable("item_id") Long itemId) {
        return shoppingCartService.removeItemFromCart(cartId, itemId);
    }

}
