package security;

import com.carrental.cloudgateway.security.JwtAuthenticationTokenConverter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationTokenConverterTest {

    @InjectMocks
    private JwtAuthenticationTokenConverter jwtAuthenticationTokenConverter;

//    @Test
//    void generateTokenTest_success() {
//        ReflectionTestUtils.setField(jwtAuthenticationTokenConverter, "signingKey", "asdffgdgftyfhfjhjgjhghjghjjhfhjfhfjhfjfh776986hgh");
//        ReflectionTestUtils.setField(jwtAuthenticationTokenConverter, "expiration", 30L);
//
//        User user = TestUtils.getResourceAsJson("/data/User.json", User.class);
//
//        assertDoesNotThrow(() -> jwtAuthenticationTokenConverter.generateToken(user));
//    }

}
