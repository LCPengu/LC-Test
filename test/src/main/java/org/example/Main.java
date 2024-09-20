package org.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import org.json.JSONObject;

public class Main {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String get_Google_URL = "https://google.com";
    private static final String get_Amazon_URL = "https://amazon.com";
    public static String[] google, amazon;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        google = sendGET(get_Google_URL);
        amazon = sendGET(get_Amazon_URL);
        server.createContext("/v1/all-status", new AllHandler());
        server.createContext("/v1/google-status", new GoogleHandler());
        server.createContext("/v1/amazon-status", new AmazonHandler());
        server.setExecutor(null);
        server.start();
        long time = 0;
        long intervalTime = 60000;
        while (true) {
            long start = System.currentTimeMillis();
            if (time >= intervalTime) {
                time -= intervalTime;
                google = sendGET(get_Google_URL);
                amazon = sendGET(get_Amazon_URL);
            }
            time += System.currentTimeMillis() - start;

        }
    }
    
    static class GoogleHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String[] response = google;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject("{ \"URL\" : \"" + response[0] + "\"," +
                        "\"Response_Code\" : \"" + response[1] + "\"," +
                        "\"Duration\" : \"" + response[2] + "\"," +
                        "\"Date\" : \"" + response[3] + "\", }");
            } catch (Exception err) {
            }
            exchange.sendResponseHeaders(200, jsonObject.toString().length());
            OutputStream os = exchange.getResponseBody();
            os.write(jsonObject.toString().getBytes());
            os.close();
        }
    }

    static class AmazonHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String[] response = amazon;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject("{ \"URL\" : \"" + response[0] + "\"," +
                        "\"Response_Code\" : \"" + response[1] + "\"," +
                        "\"Duration\" : \"" + response[2] + "\"," +
                        "\"Date\" : \"" + response[3] + "\", }");
            } catch (Exception err) {
            }
            exchange.sendResponseHeaders(200, jsonObject.toString().length());
            OutputStream os = exchange.getResponseBody();
            os.write(jsonObject.toString().getBytes());
            os.close();
        }
    }

    static class AllHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String[] responseGoogle = google;
            String [] responseAmazon = amazon;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject("{objects:[{" +
                        "\"URL\" : \"" + responseGoogle[0] + "\"," +
                        "\"Response_Code\" : \"" + responseGoogle[1] + "\"," +
                        "\"Duration\" : \"" + responseGoogle[2] + "\"," +
                        "\"Date\" : \"" + responseGoogle[3] + "\"},{" +
                        "\"URL\" : \"" + responseAmazon[0] + "\"," +
                        "\"Response_Code\" : \"" + responseAmazon[1] + "\"," +
                        "\"Duration\" : \"" + responseAmazon[2] + "\"," +
                        "\"Date\" : \"" + responseAmazon[3] + "\"}]}");
            } catch (Exception err) {
            }
            exchange.sendResponseHeaders(200, jsonObject.toString().length());
            OutputStream os = exchange.getResponseBody();
            os.write(jsonObject.toString().getBytes());
            os.close();
        }
    }

    private static String[] sendGET(String url) throws IOException {
        long start = System.currentTimeMillis();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        long elapsedTime = System.currentTimeMillis() - start;
        con.setRequestProperty("User-Agent", USER_AGENT);
        String urlConnect = con.getURL().toString();
        int responseCode = con.getResponseCode();
        long date = con.getDate();

        System.out.println("URL : " + urlConnect + "\n" +
                "Response Code : " + responseCode + "\n" +
                "Duration : " + elapsedTime + "\n" +
                "date : " + date + "\n");
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new String[] {url, Integer.toString(responseCode), Long.toString(elapsedTime), Long.toString(date)};
        } else {
            throw new IOException();
        }

    }

}