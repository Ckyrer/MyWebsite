package ru.kvdl.mywebsite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import ru.kvdl.kevlight.DHOperator;
import ru.kvdl.kevlight.Responser;
import ru.kvdl.kevlight.Server;
import ru.kvdl.kevlight.annotations.KLObserver;
import ru.kvdl.kevlight.annotations.KLRequestHandler;

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

    @KLObserver
    public boolean overwatch(String req, String[] head, String ip, Responser resp) {
        if (!req.equals("")) {
            resp.sendResponse("<script>document.location.replace('/')</script>", "200 OK");
            return false;
        }
        return true;
    }

    @KLRequestHandler(request = "")
    public void home(Responser resp) {
        final String data = getProjectsCards(new File(path+"data/projects.txt"));
        resp.sendResponse(
            DHOperator.buildPage(path+"pages/home").replace("<!-- PROJECTS HERE -->", data), 
            "200 OK"
        );
    }

    public static void main(String[] arguments) {
        Server host = new Server(new App(), 4040);

        host.start();
    }
}