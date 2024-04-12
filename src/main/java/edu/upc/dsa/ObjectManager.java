package edu.upc.dsa;

import edu.upc.dsa.models.Object;
import edu.upc.dsa.models.User;

import java.util.Date;
import java.util.List;

public interface ObjectManager {
    public User registerUser(String name, String surname, String birthDate, String email, String password);
    public List<User> getUsersOrderedAlphabetically();
    public User loginUser(String email, String password);
    public Object addObject(String name, String description, int coins);
    public List<Object> getObjectsOrderedByPrice();
    public int buyObject(String email, String objectName);
    public List<Object> getObjectsBoughtByUser(String email);
    public int size();
}
