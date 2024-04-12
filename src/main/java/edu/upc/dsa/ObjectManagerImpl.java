package edu.upc.dsa;

import edu.upc.dsa.models.Object;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.util.*;

public class ObjectManagerImpl implements ObjectManager{

    private static ObjectManagerImpl instance;
    private Map<String, User> users;
    private Map<String, Object> objects;
    final static Logger logger = Logger.getLogger(ObjectManagerImpl.class);

    private ObjectManagerImpl() {
        this.users = new HashMap<>();
        this.objects = new HashMap<>();
    }
    public static ObjectManagerImpl getInstance() {
        if (instance == null) {
            instance = new ObjectManagerImpl();
        }
        return instance;
    }

    @Override
    public User registerUser(String name, String surname, String birthDate, String email, String password) {
        logger.info("registerUser called with parameters: " + name + ", " + surname + ", " + birthDate + ", " + email + ", " + password);
        if (users.containsKey(email)) {
            logger.error("A user with this email already exists");
            return null;
        }

        User newUser = new User(name, surname, birthDate, email, password);
        newUser.setId(UUID.randomUUID().toString());
        newUser.setDsaCoins(50);
        users.put(email, newUser);
        logger.info("registerUser ended with result: " + newUser.getName());
        return newUser;
    }

    @Override
    public List<User> getUsersOrderedAlphabetically() {
        logger.info("getUsersOrderedAlphabetically called");
        List<User> userList = new ArrayList<>(users.values());

        userList.sort((u1, u2) -> {
            int surnameComparison = u1.getSurname().compareTo(u2.getSurname());
            if (surnameComparison != 0) {
                return surnameComparison;
            } else {
                return u1.getName().compareTo(u2.getName());
            }
        });
        for(User u: userList){
            logger.info(u.getName());
        }

        logger.info("getUsersOrderedAlphabetically ended with result: " + userList);
        return userList;
    }

    @Override
    public User loginUser(String email, String password) {
        logger.info("loginUser called with parameters: " + email + ", " + password);
        User user = users.get(email);
        if (user == null) {
            logger.error("User does not exist");
            return null;
        }
        if (!user.getPassword().equals(password)) {
            logger.error("Invalid password");
            return null;
        }
        logger.info("loginUser ended with result: " + user);
        return user;
    }

    @Override
    public Object addObject(String name, String description, int coins) {
        logger.info("addObject called with parameters: " + name + ", " + description + ", " + coins);
        if (objects.containsKey(name)) {
            logger.error("An object with this name already exists");
            return null;
        }

        Object newObject = new Object(name, description, coins);
        objects.put(name, newObject);
        logger.info("addObject ended with result: " + newObject);
        return newObject;
    }

    @Override
    public List<Object> getObjectsOrderedByPrice() {
        logger.info("getObjectsOrderedByPrice called");
        List<Object> objectList = new ArrayList<>(objects.values());

        objectList.sort(Comparator.comparingInt(Object::getCoins).reversed());

        logger.info("getObjectsOrderedByPrice ended with result: " + objectList);
        return objectList;
    }

    @Override
    public int buyObject(String email, String objectName) {
        logger.info("buyObject called with parameters: " + email + ", " + objectName);
        User user = users.get(email);
        if (user == null) {
            logger.error("User does not exist");
            return -1;
        }

        Object object = objects.get(objectName);
        if (object == null) {
            logger.error("Object does not exist");
            return -2;
        }

        if (user.getDsaCoins() < object.getCoins()) {
            logger.error("Not enough DSA coins");
            return -3;
        }

        user.setDsaCoins(user.getDsaCoins() - object.getCoins());
        user.getPurchasedObjects().add(object);
        logger.info("buyObject ended successfully");
        return 0;
    }

    @Override
    public List<Object> getObjectsBoughtByUser(String email) {
        logger.info("getObjectsBoughtByUser called with parameter: " + email);
        User user = users.get(email);
        if (user == null) {
            logger.error("User does not exist");
            return null;
        }
        List<Object> result = user.getPurchasedObjects();
        logger.info("getObjectsBoughtByUser ended with result: " + result);
        return result;
    }

    @Override
    public int size() {
        int ret = this.objects.size();
        logger.info("size " + ret);

        return ret;
    }
}
