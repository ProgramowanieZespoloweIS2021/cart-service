package com.pz.cartservice.carts.adapters.api;

import com.pz.cartservice.carts.adapters.api.request.ShoppingCartItemRequest;
import com.pz.cartservice.carts.adapters.api.request.SubmissionRequest;
import com.pz.cartservice.carts.adapters.api.response.ShoppingCartItemResponse;
import com.pz.cartservice.carts.adapters.api.response.ShoppingCartResponse;
import com.pz.cartservice.carts.domain.ShoppingCartService;
import com.pz.cartservice.carts.domain.entity.ShoppingCartItem;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
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
        return ShoppingCartResponse.fromShoppingCartDetails(shoppingCartService.getExistingShoppingCart(cartId));
    }


    @GetMapping("/{cart_id}/items")
    public List<ShoppingCartItemResponse> getItemsFromCart(@PathVariable("cart_id") Long cartId) {
        return shoppingCartService.getExistingShoppingCart(cartId).getItems().stream()
                .map(ShoppingCartItemResponse::fromShoppingCartItemDetails)
                .collect(Collectors.toList());
    }


    @GetMapping("/{cart_id}/items/{item_id}")
    public ShoppingCartItemResponse getItemFromCart(@PathVariable("cart_id") Long cartId,
                                                    @PathVariable("item_id") Long itemId) {
        return ShoppingCartItemResponse.fromShoppingCartItemDetails(shoppingCartService.getItemFromShoppingCart(cartId, itemId));
    }


    @PostMapping("/{cart_id}/items")
    public String addItemToCart(@PathVariable("cart_id") Long cartId,
                                @RequestBody @Valid ShoppingCartItemRequest item) {
        ShoppingCartItem shoppingCartItem = ShoppingCartItemRequest.toEntity(null, item);
        shoppingCartService.addItemToCart(cartId, shoppingCartItem);
        return "Item added to cart";
    }


    @PostMapping("/{cart_id}/items/{item_id}")
    public String editItemInCart(@PathVariable("cart_id") Long cartId,
                                 @PathVariable("item_id") Long itemId,
                                 @RequestBody @Valid ShoppingCartItemRequest item) {
        ShoppingCartItem shoppingCartItem = ShoppingCartItemRequest.toEntity(itemId, item);
        shoppingCartService.editItemInCart(cartId, shoppingCartItem);
        return "Item edited in cart";
    }


    @DeleteMapping("/{cart_id}/items/{item_id}")
    public String removeFromCart(@PathVariable("cart_id") Long cartId,
                                 @PathVariable("item_id") Long itemId) {
        shoppingCartService.removeItemFromCart(cartId, itemId);
        return "Item removed";
    }


    @PostMapping("/submission")
    public String submitCart(@RequestBody @Valid SubmissionRequest submissionRequest) {
        shoppingCartService.submitCart(submissionRequest.getCartId(), submissionRequest.getBuyerId());
        return "Cart submitted";
    }

}
