package org.jmc.dictionary.Utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.*;

public class GoogleVoiceAPI {
    public static final String GOOGLE_TRANS_AUDIO = "http://translate.google.com/translate_tts?";
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static final String userAgent =
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) "
                    + "AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";
    private static GoogleVoiceAPI voice;

    private static ExecutorService executorService;

    private GoogleVoiceAPI() {}

    public synchronized static GoogleVoiceAPI getInstance() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(3);
        }

        if (voice == null) {
            voice = new GoogleVoiceAPI();
        }
        return voice;
    }

    public InputStream getAudio(String text, String languageOutput) throws IOException {
        String urlStr = generateSpeakURL(languageOutput, text);
        URL newUrl = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) newUrl.openConnection();
        urlConn.setRequestMethod("GET");
        urlConn.setRequestProperty("User-Agent", userAgent);
        InputStream voiceSrc = urlConn.getInputStream();
        return new BufferedInputStream(voiceSrc);
    }

    private static String generateNewToken() {
        byte[] ranBytes = new byte[24];
        secureRandom.nextBytes(ranBytes);
        return base64Encoder.encodeToString(ranBytes);
    }

    public void playAudio(InputStream sound) throws JavaLayerException {
        Future<String> future = executorService.submit(new Callable<>() {
            @Override
            public String call() throws JavaLayerException {
                new Player(sound).play();
                return "completed";
            }
        });
    }

    private static String generateSpeakURL(String lang, String text) {
        return GOOGLE_TRANS_AUDIO + "?ie=UTF-8" +
                "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&tl=" + lang +
                "&tk=" + generateNewToken() +
                "&client=tw-ob";
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
