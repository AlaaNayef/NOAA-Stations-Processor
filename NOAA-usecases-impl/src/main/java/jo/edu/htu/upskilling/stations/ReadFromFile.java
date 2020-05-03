package jo.edu.htu.upskilling.stations;

import com.sun.xml.internal.ws.api.message.Packet;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFromFile {
    public static void main(String[] args) throws IOException {

        Path pwd = Paths.get(".", "StationsTest.txt");
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(pwd)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            for (int i = 0; i < lines.size(); i++) {
                System.out.println(lines.get(i).substring(0, 5) +" "+lines.get(i).substring(7,11)
                        +" "+lines.get(i).substring(13,41)+" "+lines.get(i).substring(43,46)+" "+
                        lines.get(i).substring(48,49)+" "+lines.get(i).substring(51,55)+" "+
                        lines.get(i).substring(57,63)+" "+lines.get(i).substring(65,72)+" "+
                        lines.get(i).substring(74,80)+" "+lines.get(i).substring(82,89)+" "+
                        lines.get(i).substring(91,98));
            }
        }
//        List<String> lines = new ArrayList<>();
//        Scanner sc = new Scanner(new File("Stations.txt"));
//        while (sc.hasNextLine()) {
//            lines.add(sc.nextLine().substring(49,50));
//            //lines.get(i).substring(49,50);
//        }
//        for (int i = 0; i < lines.size(); i++){
//            String content=lines.get(i).substring(49,50);
//        }

    }
}
