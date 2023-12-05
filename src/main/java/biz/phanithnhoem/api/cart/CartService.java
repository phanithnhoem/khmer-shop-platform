package biz.phanithnhoem.api.cart;

public interface CartService {

    /**
     * Adds a specified quantity of a product to the shopping cart for the given user.
     *
     * @param userUuid    The unique identifier of the user for whom the product is being added to the cart.
     * @param productUuid The unique identifier of the product to be added to the cart.
     * @param qty         The quantity of the product to be added to the cart.
     */
    void addToCart(String userUuid, String productUuid, Integer qty);

    /**
     * Removes a specified product from the shopping cart for the given user.
     *
     * @param userUuid    The unique identifier of the user from whose cart the product is to be removed.
     * @param productUuid The unique identifier of the product to be removed from the cart.
     */
    void removeFromCart(String userUuid, String productUuid);
}
