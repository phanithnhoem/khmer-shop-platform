package biz.phanithnhoem.api.cart;

import biz.phanithnhoem.api.product.Product;
import biz.phanithnhoem.api.product.ProductRepository;
import biz.phanithnhoem.api.user.User;
import biz.phanithnhoem.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void removeFromCart(String userUuid, String productUuid) {

        // Check provided user exists or not
        User user = userRepository.findByUuidAndIsDeletedFalse(userUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User uuid: %s has not been found in database.", userUuid)));
        // Check provided product exists or not
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Product uuid: %s has not been found in database.", productUuid)));

        Cart cart = user.getCart();
        if (cart != null) {
            List<CartItem> cartItemList = cart.getCartItems();
            cartItemList.removeIf(cartItem -> cartItem.getProduct().equals(product));
            cartRepository.save(cart);
        }
    }

    @Transactional
    @Override
    public void addToCart(String userUuid, String productUuid, Integer qty) {

        // Check provided user exists or not
        User user = userRepository.findByUuidAndIsDeletedFalse(userUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User uuid: %s has not been found in database.", userUuid)));
        // Check provided product exists or not
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Product uuid: %s has not been found in database.", productUuid)));

        // If user already has cart
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setIsCheckedOut(false);
            cart.setCreatedAt(Instant.now());
            cart.setTotalAmount((double) 0);
            user.setCart(cart);
            // Create new cart to user
            cartRepository.save(cart);
        }
        cart.setUpdatedAt(Instant.now());
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setQty(qty);
        if (product.getDiscountPercent() != null) {
            cartItem.setDiscountAmount(product.getDiscountPercent() * product.getPrice());
        } else {
            cartItem.setDiscountAmount(Double.parseDouble("0"));
        }
        cartItem.setSubTotal(cartItem.getDiscountAmount() * cartItem.getQty());
        cart.getCartItems().add(cartItem);
        // Save cart item into cart
        cartItemRepository.save(cartItem);
    }
}
