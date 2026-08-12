package az.trendyolaz.service;
import az.trendyolaz.dto.CartItemRequestDto;
import az.trendyolaz.dto.CartItemResponseDto;
import az.trendyolaz.dto.CartResponseDto;


public interface CartService {
    CartResponseDto getCart(String username);
    CartResponseDto addItemToCart(String username , CartItemRequestDto requestDto);
    CartResponseDto removeItemFromCart(String username , Long itemId);
    void clearCart(String username);
}
