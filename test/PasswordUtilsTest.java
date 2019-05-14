import org.junit.jupiter.api.Test;
import sun.jvm.hotspot.utilities.Assert;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {

    @Test

    void test1() {
        String salt = PasswordUtils.generateSalt();
        String pwd = "password";
        String pwd_hash = PasswordUtils.hashPassword(pwd, salt);
        assertTrue(PasswordUtils.verifyPassword(pwd,pwd_hash,salt));
    }

    @Test
    void test2() {
        String salt = PasswordUtils.generateSalt();
        String pwd = "password";
        String pwd_hash = PasswordUtils.hashPassword(pwd, salt);
        assertFalse(PasswordUtils.verifyPassword("ads",pwd_hash,salt));
    }

}