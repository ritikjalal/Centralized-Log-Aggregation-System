package com.example.orderservice.service;

import com.example.orderservice.DTO.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;


    public Order createOrder(OrderDto orderDto){

        Order order=modelMapper.map(orderDto,Order.class);
        order=orderRepository.save(order);

        return order;
    }

    public List<Order> createBulkOrder(List<OrderDto> orderDto){

        List<Order> orders = orderDto.stream()
                .map(dto -> {
                    Order order = modelMapper.map(dto, Order.class);
                    return order;
                })
                .toList();

        return orderRepository.saveAll(orders);
    }


    public Order getByorderId(Long id){
        Optional<Order> order=orderRepository.findById(id);
        return order.orElse(null);
    }


    public List<Order> getOrderList(int page,int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Order> orderPage=orderRepository.findAll(pageable);
        return orderPage.getContent();
    }
}
