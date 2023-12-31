package com.carrental.lib.mapper;

import com.carrental.dto.UserDto;
import com.carrental.entity.User;
import com.carrental.lib.util.AssertionUtils;
import com.carrental.lib.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class UserDtoMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    void mapEntityToDtoTest_success() {
        User user = TestUtils.getResourceAsJson("/data/User.json", User.class);

        UserDto userDto = assertDoesNotThrow(() -> userMapper.mapEntityToDto(user));

        assertNotNull(userDto);
        AssertionUtils.assertUser(user, userDto);
    }

    @Test
    void mapEntityToDtoTest_null() {
        UserDto userDto = assertDoesNotThrow(() -> userMapper.mapEntityToDto(null));

        assertNull(userDto);
    }

    @Test
    void mapDtoToEntityTest_success() {
        UserDto userDto = TestUtils.getResourceAsJson("/data/UserDto.json", UserDto.class);

        User user = assertDoesNotThrow(() -> userMapper.mapDtoToEntity(userDto));

        assertNotNull(user);
        AssertionUtils.assertUser(user, userDto);
    }

    @Test
    void mapDtoToEntityTest_null() {
        User user = assertDoesNotThrow(() -> userMapper.mapDtoToEntity(null));

        assertNull(user);
    }

}
