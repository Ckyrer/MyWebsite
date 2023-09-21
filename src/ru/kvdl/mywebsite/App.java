package ru.kvdl.mywebsite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import ru.kvdl.kevj.DHOperator;
import ru.kvdl.kevj.Overwatch;
import ru.kvdl.kevj.Responser;
import ru.kvdl.kevj.Server;

public class App {  
    // Путь к папке resources
    final static String path = new File("").getAbsolutePath()+"/resources/";

    public static String getProjectsCards(File dataFile) {
        String res = "";
        try {
            try (final BufferedReader f = new BufferedReader(new FileReader(dataFile))) {
                String s;
                boolean side = false;
                while ( (s=f.readLine()) != null ) {
                    res+="<div class='card "+(side?"right":"left")+"'><h1>"+(s.split("#"))[0]+"</h1><p>"+s.split("#")[1]+"</p></div>";
                    side = !side;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
        return res;
    }

    public static void main(String[] arguments) {
        Overwatch handler = (String req, String ip, String[] args, Responser resp) -> {
            if (!req.equals("")) {
                resp.sendResponse("<script>document.location.replace('/')</script>", "200 OK");
                return false;
            }
            return true;
        };

        // ПОТОМ ЗАМЕНИТЬ НА Integer.parseInt(arguments[0])
        Server host = new Server(4040, handler);

        // Главная страница
        host.addRequestHandler("", false, (String[] headers, String ip, Responser resp) -> {
            final String data = getProjectsCards(new File(path+"data/projects.txt"));
            resp.sendResponse(
                DHOperator.buildPage(path+"pages/home").replace("<!-- PROJECTS HERE -->", data), 
                "200 OK"
            );
        });

        host.start();
    }
}