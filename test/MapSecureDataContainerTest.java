import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapSecureDataContainerTest {

    @Test
    void createUser() {
        ISecureDataContainer<Integer> db = new MapSecureDataContainer<>();
        db.createUser("1","pwd");
        assertTrue(db.userExist("1"));
        assertTrue(db.userAuth("1","pwd"));
        assertFalse(db.userAuth("1","pwd_errata"));
        assertFalse(db.userAuth("2","pwd"));
        db.createUser("2","pwd2");
        assertFalse(db.userAuth("2","pwd"));
        assertTrue(db.userAuth("2","pwd2"));
    }

    @Test
    void removeUser() {
    }

    @Test
    void getSize() {
    }

    @Test
    void put() {
    }

    @Test
    void get() {
    }

    @Test
    void remove() {
    }

    @Test
    void copy() {
    }

    @Test
    void share() {
    }

    @Test
    void getIterator() {
    }

    @Test
    void userExist() {
    }

    @Test
    void userAuth() {
    }
}