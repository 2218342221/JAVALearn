package RPCDemo.client;

import RPCDemo.User;
import RPCDemo.UserService;

public class Client {

    public static void main(String[] args) {
        UserService stub =Stub.getStub(UserService.class);
        User userById = stub.getUserById(123);
        System.out.println(userById);

    }
}
