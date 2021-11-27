package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByIdAndUser_Id(Long id, Long userId);

    List<Order> findAllByUser_Id(Long userId, Pageable pageable);

}
