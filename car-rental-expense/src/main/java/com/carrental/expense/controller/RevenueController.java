package com.carrental.expense.controller;

import com.carrental.dto.RevenueDto;
import com.carrental.expense.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/revenues")
@CrossOrigin(origins = "${cross-origin}")
public class RevenueController {

    private final RevenueService revenueService;

    @GetMapping
    public ResponseEntity<List<RevenueDto>> findAllRevenues() {
        List<RevenueDto> revenueDtoList = revenueService.findAllRevenues();

        return ResponseEntity.ok(revenueDtoList);
    }

    @GetMapping(path = "/total")
    public ResponseEntity<Double> getTotalAmount() {
        Double totalAmount = revenueService.getTotalAmount();

        return ResponseEntity.ok(totalAmount);
    }

    @GetMapping(path = "/{date}")
    public ResponseEntity<List<RevenueDto>> findRevenuesByDate(@PathVariable("date") LocalDate date) {
        List<RevenueDto> revenues = revenueService.findRevenuesByDate(date);

        return ResponseEntity.ok(revenues);
    }

}
