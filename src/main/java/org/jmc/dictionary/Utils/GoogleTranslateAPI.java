package org.jmc.dictionary.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleTranslateAPI {
    public static final String GOOGLE_TRANS_URL = "http://translate.google.com/translate_a/t?";
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static final String userAgent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; "
            + "en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";

    private static final Pattern WORD_PATTERN = Pattern.compile(
            "\\[\"(.*)\"]"
    );

    private static final Pattern AUTO_ENDING_PATTERN = Pattern.compile(
            "(.*)\",\".{2,5}$"
    );

    public enum LANGUAGE {
        ENGLISH("en"), VIETNAMESE("vi"), AUTO("auto");

        private String lang = "";

        LANGUAGE(String lang) {
            this.lang = lang;
        }

        @Override
        public String toString() {
            return this.lang;
        }
    }

    private static ExecutorService executorService;
    private static String translatedWord = "";

    public static String translate(String query, LANGUAGE srcLang, LANGUAGE destLang) throws IOException, TimeoutException {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(1);
        }

        final String finalQuery = query.replace("\n", " ");

        Future<String> future = executorService.submit(new Callable<>() {
            @Override
            public String call() throws IOException {
                String strUrl = generateTransURL(srcLang.toString(), destLang.toString(), finalQuery);
                URL url = new URL(strUrl);

                StringBuilder response = new StringBuilder();
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", userAgent);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLn;
                while ((inputLn = in.readLine()) != null) {
                    response.append(inputLn);
                }
                in.close();

                String transWord = "";
                Matcher newMatcher = WORD_PATTERN.matcher(response.toString());
                if (newMatcher.find()) {
                    transWord = newMatcher.group(1);
                }

                transWord = transWord.trim().replace("\\", "");

                newMatcher = AUTO_ENDING_PATTERN.matcher(transWord);
                if (newMatcher.find()) {
                    transWord = newMatcher.group(1);
                }

                translatedWord = transWord;
                return "completed";
            }
        });


        try {
            String completed = future.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException(e);
        } catch (TimeoutException e) {
            throw new TimeoutException();
        }

        return translatedWord;
    }

    public static String translateWithInternetCheck(String textToTranslate) {
        try {
            return GoogleTranslateAPI.translate(textToTranslate, LANGUAGE.ENGLISH, LANGUAGE.VIETNAMESE);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("No Internet");
            return "Error during translation";
        }
    }

    private static String generateTransURL(String sourceLang, String targetLang, String text) {
        return GOOGLE_TRANS_URL + "client=gtrans" +
                "&sl=" + sourceLang +
                "&tl=" + targetLang +
                "&hl=" + targetLang +
                "&tk=" + generateNewToken() +
                "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

    private static String generateNewToken() {
        byte[] ranBytes = new byte[24];
        secureRandom.nextBytes(ranBytes);
        return base64Encoder.encodeToString(ranBytes);
    }

    public static void shutdownExecutorService() {
        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }
    }
}