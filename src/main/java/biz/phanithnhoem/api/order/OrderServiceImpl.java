package biz.phanithnhoem.api.order;

import biz.phanithnhoem.api.cart.Cart;
import biz.phanithnhoem.api.cart.CartRepository;
import biz.phanithnhoem.api.user.User;
import biz.phanithnhoem.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Transactional
    @Override
    public Order checkout(String userUuid) {

        // Check provided user exists or not
        User user = userRepository.findByUuidAndIsDeletedFalse(userUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User uuid: %s has not been found in database.", userUuid)));

        Cart cart = user.getCart();
        if (cart != null && !cart.getCartItems().isEmpty()){
            // Create an order and copy cart items to order items
            Order order = new Order();
            List<OrderItem> orderItems = cart.getCartItems().stream()
                    .map(cartItem -> OrderItem.builder()
                            .product(cartItem.getProduct())
                            .qty(cartItem.getQty())
                            .isDiscountApplied(cartItem.getDiscountAmount() == null)
                            .order(order)
                            .subTotal(cartItem.getSubTotal())
                            .build())
                    .toList();
            order.setOrderItems(orderItems);
            order.setOrderDate(Instant.now());
            order.setUser(user);

            // Clear items from existing cart
            cart.getCartItems().clear();
            cartRepository.save(cart);

            // Save the order to database
            orderRepository.save(order);
            return order;
        }
        return null;
    }
}
