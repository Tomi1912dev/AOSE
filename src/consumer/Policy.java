package consumer;

/**
 * Type of strategy for the consumer agent.
 * For example, the RENEWABLE strategy means that
 * the consumer wants to buy renewable energy at the lowest price.
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 */
public enum Policy {
    PRICE,
    RENEWABLE;
}
