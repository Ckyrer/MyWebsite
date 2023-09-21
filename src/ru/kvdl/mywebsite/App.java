package ru.kvdl.mywebsite;

import java.io.File;

import ru.kvdl.kevj.DHOperator;
import ru.kvdl.kevj.Overwatch;
import ru.kvdl.kevj.Responser;
import ru.kvdl.kevj.Server;

public class App {
    public static void main(String[] arguments) {
        // Путь к папке resources
        final String path = new File("").getAbsolutePath()+"/resources/";

        Overwatch handler = (String req, String ip, String[] args, Responser resp) -> {
            System.out.println(ip+" "+req);
            return true;
        };

        // ПОТОМ ЗАМЕНИТЬ НА Integer.parseInt(arguments[0])
        Server host = new Server(4040, handler);

        // Главная страница
        host.addRequestHandler("", false, (String[] headers, String ip, Responser resp) -> {
            resp.sendResponse(DHOperator.buildPage(path+"pages/home"), "200 OK");
        });

        // 404
        host.addRequestHandler("404", false, (String[] headers, String ip, Responser resp) -> {
            resp.sendResponse(DHOperator.buildPage(path+"pages/404"), "200 OK");
        });

        host.start();
    }
}