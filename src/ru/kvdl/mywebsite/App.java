package ru.kvdl.mywebsite;

import ru.kvdl.kevj.Overwatch;
import ru.kvdl.kevj.Responser;
import ru.kvdl.kevj.Server;

public class App {
    public static void main(String[] arguments) {
        Overwatch handler = (String req, String ip, String[] args, Responser resp) -> {
            System.out.println(ip+" "+req);
            return true;
        };

        Server host = new Server(7070, handler);

        // Главная страница
        host.addRequestHandler("", false, (String[] headers, String ip, Responser resp) -> {

        });

        host.addRequestHandler("404", false, (String[] headers, String ip, Responser resp) -> {
            
        });

    }
}