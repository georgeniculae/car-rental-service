package com.carrental.expense.repository;

import com.carrental.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query("""
            From Revenue revenue where
            revenue.dateOfRevenue = :dateOfRevenue""")
    List<Revenue> findByDateOfRevenue(@Param("dateOfRevenue") LocalDate dateOfRevenue);

    @Query("SELECT sum(revenue.amountFromBooking) from Revenue revenue")
    Double getTotalAmount();

}
