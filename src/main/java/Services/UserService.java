package Services;

import Repositories.UserRepository;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;


    public List<User> getUserByMail(String mail) {
        return repo.findByMail(mail);
    }

    @Transactional
    public User registerUser(User user){
        return repo.save(user);
    }


}
