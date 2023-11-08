package com.carrental.agency.util;

import com.carrental.dto.BranchDto;
import com.carrental.dto.CarDto;
import com.carrental.dto.CarStatusEnum;
import com.carrental.dto.EmployeeDto;
import com.carrental.dto.RentalOfficeDto;
import com.carrental.entity.BodyType;
import com.carrental.entity.Branch;
import com.carrental.entity.Car;
import com.carrental.entity.CarStatus;
import com.carrental.entity.Employee;
import com.carrental.entity.RentalOffice;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionUtils {

    public static void assertCar(Car car, CarDto carDto) {
        assertEquals(car.getMake(), carDto.getMake());
        assertEquals(car.getModel(), carDto.getModel());
        assertBodyType(car.getBodyType(), Objects.requireNonNull(carDto.getBodyType()));
        assertEquals(car.getYearOfProduction(), carDto.getYearOfProduction());
        assertEquals(car.getColor(), carDto.getColor());
        assertEquals(car.getMileage(), carDto.getMileage());
        assertCarStatus(car.getCarStatus(), Objects.requireNonNull(carDto.getCarStatus()));
        assertEquals(car.getAmount(), Objects.requireNonNull(carDto.getAmount()).doubleValue());
        assertEquals(car.getUrlOfImage(), carDto.getUrlOfImage());
    }

    public static void assertBranch(Branch branch, BranchDto branchDto) {
        assertEquals(branch.getName(), branchDto.getName());
        assertEquals(branch.getAddress(), branchDto.getAddress());
    }

    public static void assertRentalOffice(RentalOffice rentalOffice, RentalOfficeDto rentalOfficeDto) {
        assertEquals(rentalOffice.getName(), rentalOfficeDto.getName());
        assertEquals(rentalOffice.getContactAddress(), rentalOfficeDto.getContactAddress());
        assertEquals(rentalOffice.getLogoType(), rentalOfficeDto.getLogoType());
    }

    public static void assertEmployee(Employee employee, EmployeeDto employeeDto) {
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getJobPosition(), employeeDto.getJobPosition());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
    }

    private static void assertBodyType(BodyType bodyType, CarDto.BodyTypeEnum bodyTypeEnum) {
        assertEquals(bodyType.getDisplayName(), bodyTypeEnum.getValue());
    }

    private static void assertCarStatus(CarStatus carStatus, CarStatusEnum carStatusEnum) {
        assertEquals(carStatus.getDisplayName(), carStatusEnum.getValue());
    }

}