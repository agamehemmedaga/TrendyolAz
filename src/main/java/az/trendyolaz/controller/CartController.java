package az.trendyolaz.controller;

import az.trendyolaz.dto.CartItemRequestDto;
import az.trendyolaz.dto.CartResponseDto;
import az.trendyolaz.service.CartService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/")
@RequiredArgsConstructor

public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(Authentication authentication){
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addItemToCart(Authentication authentication , @Valid @RequestBody CartItemRequestDto requestDto){
        return ResponseEntity.ok(cartService.addItemToCart(authentication.getName() , requestDto));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponseDto> removeItemFromCart(Authentication authentication , @PathVariable Long itemId){
        return ResponseEntity.ok(cartService.removeItemFromCart(authentication.getName() , itemId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartResponseDto> clearCart(Authentication authentication){
        cartService.clearCart(authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
