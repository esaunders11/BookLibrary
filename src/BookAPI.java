import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.File;


public class BookAPI {

    public static String[] searchBook(String search) {

        try {
            search = search.replace(" ", "+");
            System.out.println(search);
            String s = "https://openlibrary.org/search.json?q=" + search;
            URI u = new URI(s);
            URL url = u.toURL();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String json = reader.lines().collect(Collectors.joining());

                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(json);
                String[] info = new String[2];

                while (parser.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = parser.currentName();

                    if("docs".equals(fieldName)) {
                        parser.nextToken();
                    }
                    if ("first_publish_year".equals(fieldName)) {
                        parser.nextToken();
                        info[0] = parser.getValueAsString();
                        parser.nextToken();
                    }
                    if ("first_sentence".equals(fieldName)) {
                        parser.nextToken();
                        info[1] = parser.getValueAsString();
                        break;
                    }
                }

                return info;
            } 

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}