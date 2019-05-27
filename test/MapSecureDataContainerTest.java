import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapSecureDataContainerTest {

    @Test
    void userExist() {
        ISecureDataContainer<Integer> db = new MapSecureDataContainer<>();
        assertFalse(db.userExist("1"));
        assertThrows(IllegalArgumentException.class,() -> {
            db.userExist(null);
        });
        db.createUser("1","pwd");
        assertTrue(db.userExist("1"));
    }

    @Test
    void userAuth() {
        ISecureDataContainer<Integer> db = new MapSecureDataContainer<>();
        assertThrows(IllegalArgumentException.class,() -> {
            db.userAuth(null,"asd");
        });
        assertThrows(IllegalArgumentException.class,() -> {
            db.userAuth("asd",null);
        });
        db.createUser("1","pwd");
        assertFalse(db.userAuth("1","pwd_err"));
        assertTrue(db.userAuth("1","pwd"));
    }

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
        assertThrows(IllegalArgumentException.class,() -> {
            db.createUser("2","pwd");
        });
        assertThrows(IllegalArgumentException.class,() -> {
            db.createUser(null,"pwd");
        });
        assertThrows(IllegalArgumentException.class,() -> {
            db.createUser("2",null);
        });
    }

    @Test
    void removeUser() {
        ISecureDataContainer<Integer> db = new MapSecureDataContainer<>();
        assertThrows(IllegalArgumentException.class,() -> {
            db.removeUser(null,"pwd");
        });
        assertThrows(IllegalArgumentException.class,() -> {
            db.removeUser("asd",null);
        });
        Integer i = new Integer(1);
        db.createUser("1","pwd");
        db.createUser("2","pwd");
        db.put("1","pwd",i);
        assertFalse(db.put("1","pwd",i));
        assertTrue(db.put("1","pwd",new Integer(1)));
        assertFalse(db.put("2","pwd",i));
        assertTrue(db.put("2","pwd",new Integer(1)));
        db.removeUser("2","pwd");
        assertThrows(IllegalArgumentException.class,() -> {
            db.removeUser("1","pwd_err");
        });
        db.removeUser("1","pwd");
        assertFalse(db.userExist("1"));
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

}