package app.order.service;

import app.order.model.Order;
import app.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAllUserOrders(UUID ownerId) {

        return orderRepository.findAllByOwnerId(ownerId);
    }

}
