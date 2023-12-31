package com.carrental.expense.mapper;

import com.carrental.expense.util.AssertionUtils;
import com.carrental.expense.util.TestUtils;
import com.carrental.entity.Revenue;
import com.carrental.dto.RevenueDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RevenueMapperTest {

    private final RevenueMapper rentalOfficeMapper = new RevenueMapperImpl();

    @Test
    void mapEntityToDtoTest_success() {
        Revenue revenue = TestUtils.getResourceAsJson("/data/Revenue.json", Revenue.class);

        RevenueDto revenueDto = rentalOfficeMapper.mapEntityToDto(revenue);

        assertNotNull(revenueDto);
        AssertionUtils.assertRevenue(revenue, revenueDto);
    }

    @Test
    void mapDtoToEntityTest_success() {
        RevenueDto revenueDto = TestUtils.getResourceAsJson("/data/RevenueDto.json", RevenueDto.class);

        Revenue revenue = rentalOfficeMapper.mapDtoToEntity(revenueDto);

        assertNotNull(revenue);
        AssertionUtils.assertRevenue(revenue, revenueDto);
    }

}
