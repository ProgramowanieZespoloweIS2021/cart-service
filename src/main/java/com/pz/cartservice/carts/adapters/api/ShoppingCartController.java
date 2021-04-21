package com.pz.cartservice.carts.adapters.api;

import com.pz.cartservice.carts.domain.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// TODO: better api design??
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
    public ShoppingCartResponse getCart(@PathVariable("cart_id") Long cartId) {
        return ShoppingCartResponse.fromEntity(shoppingCartService.getExistingShoppingCart(cartId));
    }


    @GetMapping("/{cart_id}/{item_id}")
    public ShoppingCartItemResponse getItemFromCart(@PathVariable("cart_id") Long cartId, @PathVariable("item_id") Long itemId) {
        return ShoppingCartItemResponse.fromEntity(shoppingCartService.getItemFromShoppingCart(cartId, itemId));
    }


    @PostMapping("/{cart_id}")
    public Long addItemToCart(@PathVariable("cart_id") Long cartId, @RequestBody @Valid ShoppingCartItemRequest item) {
        return shoppingCartService.addItemToCart(cartId, item);
    }


    @PostMapping("/{cart_id}/{item_id}")
    public Long editItemInCart(@PathVariable("cart_id") Long cartId, @PathVariable("item_id") Long itemId, @RequestBody @Valid ShoppingCartItemRequest item) {
        return shoppingCartService.editItemInCart(cartId, itemId, item);
    }


    @DeleteMapping("/{cart_id}/{item_id}")
    public Long removeFromCart(@PathVariable("cart_id") Long cartId, @PathVariable("item_id") Long itemId) {
        return shoppingCartService.removeItemFromCart(cartId, itemId);
    }


    @PostMapping("/submission")
    public String submitCart(@RequestBody @Valid SubmissionRequest submissionRequest) {
        shoppingCartService.submitCart(submissionRequest.getCartId(), submissionRequest.getBuyerId());
        return "Cart submitted";
    }

}
