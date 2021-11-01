
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class Main {
    static String SOURCE_URI = "https://assets-cdn.breezy.hr/breezy-portal/javascripts/v3/translate.breezy.js";

    static String getSource() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(SOURCE_URI)))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException error) {
            return null;
        }
    }

    public static void main(String[] args) {
        JSObjectHashMap translations = new TranslationObjectFinder(getSource(), SOURCE_URI).find();
        HashMap<String, String> ptbr = (HashMap<String, String>) translations.get("pt-br");

        System.out.println(ptbr.get("%LABEL_POSITION_TYPE_WORLDWIDE%"));
    }
}
