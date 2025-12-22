package com.fedbacknow.fedbacknow.service;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import com.fedbacknow.fedbacknow.domain.SentimentType;
import com.fedbacknow.fedbacknow.util.TextPreprocessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.LongBuffer;
import java.util.*;

@Service
public class OnnxSentimentService {

    private static final int MAX_LEN = 128;

    private final OrtEnvironment env;
    private final OrtSession session;
    private final Map<String, Integer> vocab;

    public OnnxSentimentService() {
        try {
            // 1️⃣ Inicializa ambiente ONNX
            this.env = OrtEnvironment.getEnvironment();

            // 2️⃣ Carrega modelo
            var modelResource = new ClassPathResource("model/model.onnx");
            byte[] modelBytes = modelResource.getInputStream().readAllBytes();

            this.session = env.createSession(modelBytes, new OrtSession.SessionOptions());

            // 3️⃣ Carrega vocab.txt
            this.vocab = loadVocab();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao iniciar ONNX Sentiment Service", e);
        }
    }

    // =========================
    // MÉTODO PRINCIPAL
    // =========================
    public SentimentType analyze(String rawText) {

        try {
            // 🔹 Pré-processa texto (Instagram/Facebook)
            String text = TextPreprocessor.normalize(rawText);

            long[][] inputIds = new long[1][MAX_LEN];
            long[][] attentionMask = new long[1][MAX_LEN];

            tokenize(text, inputIds, attentionMask);

            // 🔹 Tensores
            OnnxTensor inputIdsTensor =
                    OnnxTensor.createTensor(env, LongBuffer.wrap(flatten(inputIds)), new long[]{1, MAX_LEN});

            OnnxTensor attentionMaskTensor =
                    OnnxTensor.createTensor(env, LongBuffer.wrap(flatten(attentionMask)), new long[]{1, MAX_LEN});

            Map<String, OnnxTensor> inputs = Map.of(
                    "input_ids", inputIdsTensor,
                    "attention_mask", attentionMaskTensor
            );

            // 🔹 Executa modelo
            OrtSession.Result result = session.run(inputs);

            float[][] logits = (float[][]) result.get(0).getValue();

            float[] probs = softmax(logits[0]);

            int idx = argMax(probs);

            return switch (idx) {
                case 0 -> SentimentType.NEGATIVE;
                case 1 -> SentimentType.NEUTRAL;
                case 2 -> SentimentType.POSITIVE;
                default -> SentimentType.NEUTRAL;
            };

        } catch (Exception e) {
            throw new RuntimeException("Erro ao analisar sentimento", e);
        }
    }

    // =========================
    // TOKENIZAÇÃO BERT SIMPLES
    // =========================
    private void tokenize(String text, long[][] inputIds, long[][] attentionMask) {

        List<String> tokens = new ArrayList<>();
        tokens.add("[CLS]");
        tokens.addAll(Arrays.asList(text.split(" ")));
        tokens.add("[SEP]");

        for (int i = 0; i < MAX_LEN; i++) {

            if (i < tokens.size()) {
                String token = tokens.get(i);
                inputIds[0][i] = vocab.getOrDefault(token, vocab.get("[UNK]"));
                attentionMask[0][i] = 1;
            } else {
                inputIds[0][i] = 0;
                attentionMask[0][i] = 0;
            }
        }
    }

    // =========================
    // SOFTMAX
    // =========================
    private float[] softmax(float[] logits) {

        float max = Float.NEGATIVE_INFINITY;
        for (float v : logits) max = Math.max(max, v);

        float sum = 0f;
        float[] exp = new float[logits.length];

        for (int i = 0; i < logits.length; i++) {
            exp[i] = (float) Math.exp(logits[i] - max);
            sum += exp[i];
        }

        for (int i = 0; i < exp.length; i++) {
            exp[i] /= sum;
        }

        return exp;
    }

    private int argMax(float[] array) {
        int idx = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[idx]) idx = i;
        }
        return idx;
    }

    // =========================
    // UTILITÁRIOS
    // =========================
    private Map<String, Integer> loadVocab() throws Exception {

        Map<String, Integer> map = new HashMap<>();

        var vocabResource = new ClassPathResource("model/vocab.txt");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(vocabResource.getInputStream()))) {

            String line;
            int idx = 0;
            while ((line = br.readLine()) != null) {
                map.put(line.trim(), idx++);
            }
        }
        return map;
    }

    private long[] flatten(long[][] arr) {
        long[] flat = new long[arr[0].length];
        System.arraycopy(arr[0], 0, flat, 0, arr[0].length);
        return flat;
    }
}
