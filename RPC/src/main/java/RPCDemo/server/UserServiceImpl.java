package RPCDemo.server;

import RPCDemo.User;
import RPCDemo.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(Integer id) {
        return new User(id,"shawn");
    }
}
