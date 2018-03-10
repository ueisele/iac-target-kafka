package codeexamples.kafka.producer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomQuoteReader {

    private static final String QUOTE_URL = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=";

    public Quote randomQuote() throws IOException {
        JSONObject jsonObject = readJsonFromUrl(QUOTE_URL + 1);
        Quote quote = new Quote(
                jsonObject.getJSONArray("all").getJSONObject(0).getString("title"),
                jsonObject.getJSONArray("all").getJSONObject(0).getString("content"));
        return quote;
    }

    public Set<Quote> randomQuotes(int count) throws IOException {
        JSONObject jsonObject = readJsonFromUrl(QUOTE_URL + count);
        JSONArray jsonArray = jsonObject.getJSONArray("all");
        Set<Quote> quotes = new HashSet<>();
        for(int i=0;i<jsonArray.length();i++) {
            Quote quote = new Quote(
                    jsonArray.getJSONObject(i).getString("title"),
                    jsonArray.getJSONObject(i).getString("content"));
            quotes.add(quote);
        }
        return quotes;
    }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try(InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject("{\"all\":" + jsonText + "}");
            return json;
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        RandomQuoteReader randomQuoteReader = new RandomQuoteReader();

        System.out.println(randomQuoteReader.randomQuote());
    }

}
