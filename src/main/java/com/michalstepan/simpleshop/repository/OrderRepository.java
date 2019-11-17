package com.michalstepan.simpleshop.repository;

import com.michalstepan.simpleshop.domain.entity.OrderEntity;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByCreatedBetween(DateTime from, DateTime to);
}
