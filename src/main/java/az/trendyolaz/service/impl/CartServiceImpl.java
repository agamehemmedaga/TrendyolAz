package az.trendyolaz.service.impl;
import az.trendyolaz.dto.CartItemRequestDto;
import az.trendyolaz.dto.CartItemResponseDto;
import az.trendyolaz.dto.CartResponseDto;
import az.trendyolaz.exception.ResourceNotFoundException;
import az.trendyolaz.entity.Cart;
import az.trendyolaz.entity.CartItem;
import az.trendyolaz.entity.Product;
import az.trendyolaz.repository.CartRepository;
import az.trendyolaz.repository.ProductRepository;
import az.trendyolaz.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CartServiceImpl  implements CartService{

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional

    public CartResponseDto getCart(String username){
        Cart cart = getOrCreateCart(username);
        return mapToResponseDto(cart);
    }

    @Override
    @Transactional

    public CartResponseDto addItemToCart(String username , CartItemRequestDto requestDto){
        Cart cart = getOrCreateCart(username);

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Məhsul tapılmadı ID: " + requestDto.getProductId()));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item-> item.getProduct().getId().equals(product.getId()))
                .findFirst();

                if(existingItem.isPresent()){
                    CartItem item = existingItem.get();
                    item.setQuantity(item.getQuantity()+ requestDto.getQuantity());
                    item.setTotalPrice(product.getDiscountedPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }else {
                    CartItem newItem = CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .quantity(requestDto.getQuantity())
                            .totalPrice(product.getDiscountedPrice().multiply(BigDecimal.valueOf(requestDto.getProductId())))
                            .build();
                    cart.getItems().add(newItem);
                }

                recalculateGrandTotal(cart);
                Cart saved = cartRepository.save(cart);
                return mapToResponseDto(saved);
    }

    @Override
    @Transactional

    public CartResponseDto removeItemFromCart(String username , Long itemId){
        Cart cart = getOrCreateCart(username);
        boolean removed = cart.getItems().removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new ResourceNotFoundException("Səbət elementi tapılmadı ID: " + itemId);
        }

        recalculateGrandTotal(cart);
         Cart saved = cartRepository.save(cart);
         return mapToResponseDto(saved);
    }

    @Override
    @Transactional

    public void clearCart(String username){
        Cart cart = getOrCreateCart(username);
        cart.getItems().clear();
        cart.setGrandTotal(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(String username){
        return cartRepository.findByUsername(username)
                .orElseGet(()->cartRepository.save(Cart.builder()
                        .username(username)
                        .items(new ArrayList<>())
                        .grandTotal(BigDecimal.ZERO)
                        .build()));
    }

    private void recalculateGrandTotal (Cart cart){
        BigDecimal grandTotal = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO , BigDecimal::add);
        cart.setGrandTotal(grandTotal);

    }

    private CartResponseDto mapToResponseDto(Cart cart){
        return CartResponseDto.builder()
                .id(cart.getId())
                .username(cart.getUsername())
                .grandTotal(cart.getGrandTotal())
                .items(cart.getItems().stream().map(item->CartItemResponseDto.builder()
                .id(item.getId())
                        .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .unitPrice(item.getProduct().getDiscountedPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .build()).toList())
                .build();
    }
    }


