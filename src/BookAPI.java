import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookAPI {

    public static String[] searchBook(String search) {
        try {
            search = search.replace(" ", "+");
            String s = "https://openlibrary.org/search.json?q=" + search;
            URI u = new URI(s);
            URL url = u.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                //JSONParser parse = new JSONParser();
                //JSONObject data_obj = (JSONObject) parse.parse(inline);

                ObjectMapper objectMapper = new ObjectMapper();

                String[] info = new String[2];

                try {
                    JsonNode rootNode = objectMapper.readTree(inline);

                    String publishYear = rootNode.path("first_publish_year").asText();
                    String firstSentence = rootNode.path("first_sentence").asText();

                    info[0] = publishYear;
                    info[1] = firstSentence;
                } catch(IOException e) {
                    e.printStackTrace();
                    return null;
                }

                //Get the required object from the above created object
                //JSONArray data = (JSONArray) data_obj.get("docs");

                //Get the required data using its key

                //JSONObject new_obj = (JSONObject) data.get(0);

                //String publishYear = "" + new_obj.get("first_publish_year");
                //String firstSentence = "" + new_obj.get("first_sentence");
                //info[0] = publishYear;
                //info[1] = firstSentence;

                return info;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}