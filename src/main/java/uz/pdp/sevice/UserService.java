package uz.pdp.sevice;

import uz.pdp.DB.DB;
import uz.pdp.entity.User;

public class UserService {
    public boolean register(User user) {
        if (!checkUniqueUser(user.getUsername(),user.getPhoneNumber())) {
            DB.USERS.add(user);
            return true;
        }
        return false;
    }

    private boolean checkUniqueUser(String username, String phoneNumber) {
        return DB.USERS.stream().anyMatch(u -> u.getUsername().equals(username) || u.getPhoneNumber().equals(phoneNumber));
    }
}
