package com.carrental.expense.service;

import com.carrental.dto.RevenueDto;
import com.carrental.entity.Revenue;
import com.carrental.expense.mapper.RevenueMapper;
import com.carrental.expense.mapper.RevenueMapperImpl;
import com.carrental.expense.repository.RevenueRepository;
import com.carrental.expense.util.AssertionUtils;
import com.carrental.expense.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RevenueServiceTest {

    @InjectMocks
    private RevenueService revenueService;

    @Mock
    private RevenueRepository revenueRepository;

    @Spy
    private RevenueMapper revenueMapper = new RevenueMapperImpl();

    @Test
    void findAllRevenuesTest_success() {
        Revenue revenue = TestUtils.getResourceAsJson("/data/Revenue.json", Revenue.class);

        when(revenueRepository.findAll()).thenReturn(List.of(revenue));

        assertDoesNotThrow(() -> revenueService.findAllRevenues());
        List<RevenueDto> revenueDtoList = revenueService.findAllRevenues();

        AssertionUtils.assertRevenue(revenue, revenueDtoList.get(0));

        verify(revenueMapper, times(2)).mapEntityToDto(any(Revenue.class));
    }

    @Test
    void findRevenueByDateTest_success() {
        Revenue revenue = TestUtils.getResourceAsJson("/data/Revenue.json", Revenue.class);

        when(revenueRepository.findByDateOfRevenue(any(LocalDate.class))).thenReturn(List.of(revenue));

        List<RevenueDto> revenueDtoList =
                assertDoesNotThrow(() -> revenueService.findRevenuesByDate(LocalDate.parse("2050-02-20")));

        AssertionUtils.assertRevenue(revenue, revenueDtoList.get(0));

        verify(revenueMapper, times(1)).mapEntityToDto(any(Revenue.class));
    }

}