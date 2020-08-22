package RPCDemo.server;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                2,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );


        while (true) {
            Socket accept = serverSocket.accept();
            threadPoolExecutor.execute(() -> {
                try {
                    process(accept);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        accept.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private static void process(Socket s) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        InputStream inputStream = s.getInputStream();
        OutputStream outputStream = s.getOutputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ObjectOutputStream dataOutputStream = new ObjectOutputStream(outputStream);

        String className = objectInputStream.readUTF();
        String methodName = objectInputStream.readUTF();
        Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
        Object[] args = (Object[]) objectInputStream.readObject();


        Class<?> serviceImplName = getServiceImplName(className);
        if (serviceImplName != null) {
            Method method = serviceImplName.getMethod(methodName, parameterTypes);
            Object o = method.invoke(serviceImplName.newInstance(), args);
            dataOutputStream.writeObject(o);
        } else {
            dataOutputStream.writeObject(null);
        }

        dataOutputStream.flush();
        dataOutputStream.close();
        objectInputStream.close();

    }

    private static Class<?> getServiceImplName(String name) {

        if ("RPCDemo.UserService".equals(name)) {
            return UserServiceImpl.class;
        }

        return null;
    }
}
