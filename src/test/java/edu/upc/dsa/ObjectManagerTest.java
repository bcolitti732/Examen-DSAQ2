package edu.upc.dsa;

import edu.upc.dsa.models.Object;
import edu.upc.dsa.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ObjectManagerTest {
    private ObjectManagerImpl objectManager;

    @Before
    public void setUp() {
        objectManager = ObjectManagerImpl.getInstance();
    }

    @Test
    public void testRegisterUser() {
        User user1 = objectManager.registerUser("Bru", "Colitti", "2000-01-01", "brunocolitti@gmail.com", "password");
        Assert.assertNotNull(user1);

        User user2 = objectManager.registerUser("Bruno", "Caperoni", "2000-01-02", "brunocolitti@gmail.com", "password");
        Assert.assertNull(user2);
    }

    @Test
    public void testLoginUserErrors() {
        User user1 = objectManager.loginUser("nonexistent.email@example.com", "password");
        Assert.assertNull(user1);

        objectManager.registerUser("Bru", "Colitti", "2000-01-01", "brunocolitti@gmail.com", "password");

        User user2 = objectManager.loginUser("brunocolitti@gmail.com", "wrongpassword");
        Assert.assertNull(user2);
    }

    @Test
    public void testAddObject() {
        Object object = objectManager.addObject("Object1", "Description1", 100);
        Assert.assertEquals("Object1", object.getName());
        Assert.assertEquals("Description1", object.getDescription());
        Assert.assertEquals(100, object.getCoins());
    }

    @Test
    public void testGetUsersOrderedAlphabetically() {
        objectManager.registerUser("Bru", "Colitti", "2000-01-01", "brunocolitti@gmail.com", "password");
        objectManager.registerUser("John", "Doe", "2000-01-02", "johndoe@gmail.com", "password");
        objectManager.registerUser("Jane", "Doe", "2000-01-03", "janedoe@gmail.com", "password");
        objectManager.registerUser("Alice", "Smith", "2000-01-04", "alicesmith@gmail.com", "password");

        List<User> users = objectManager.getUsersOrderedAlphabetically();

        Assert.assertEquals("Colitti", users.get(0).getSurname());
        Assert.assertEquals("Bru", users.get(0).getName());
        Assert.assertEquals("Doe", users.get(1).getSurname());
        Assert.assertEquals("Jane", users.get(1).getName());
        Assert.assertEquals("Doe", users.get(2).getSurname());
        Assert.assertEquals("John", users.get(2).getName());
        Assert.assertEquals("Smith", users.get(3).getSurname());
        Assert.assertEquals("Alice", users.get(3).getName());
    }

    @Test
    public void testBuyObject() {
        objectManager.buyObject("john.doe@example.com", "Object1");
        List<Object> objects = objectManager.getObjectsBoughtByUser("john.doe@example.com");
        Assert.assertTrue(objects.stream().anyMatch(o -> o.getName().equals("Object1")));
    }
}