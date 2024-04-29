package org.jmc.dictionary.Utils;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;
import java.util.concurrent.*;

public class TextToSpeech {
    private static Synthesizer synthesizer;
    private static ExecutorService executorService;

    public static void speak(String word) {
        if (word.isEmpty()) {
            return;
        }

        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(3);
        }

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.setProperty(
                        "freetts.voices",
                        "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory"
                );

                Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

                synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
                synthesizer.allocate();
                synthesizer.resume();

                synthesizer.speakPlainText(word, null);
                synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
                return "completed";
            }
        });
    }

    public static void deallocateSynthesizer() {
        if (synthesizer == null) {
            return;
        }

        try {
            synthesizer.deallocate();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
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

    public static void shutDown() {
        deallocateSynthesizer();
        shutdownExecutorService();
    }
}
