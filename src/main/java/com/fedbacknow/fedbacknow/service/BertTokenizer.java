package com.fedbacknow.fedbacknow.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BertTokenizer {

    public Map<String, Object> prepareInput(String text) {
        // Tamanho fixo do BERT
        int maxLength = 128;

        // Criar arrays
        long[][] inputIds = new long[1][maxLength];
        long[][] attentionMask = new long[1][maxLength];

        // Token [CLS] no início
        inputIds[0][0] = 101;  // ID do [CLS]
        attentionMask[0][0] = 1;

        // Se tiver texto, processar
        if (text != null && !text.trim().isEmpty()) {
            text = text.toLowerCase().trim();
            String[] words = text.split("\\s+");

            int pos = 1;
            for (int i = 0; i < words.length && pos < maxLength - 1; i++) {
                String word = words[i].replaceAll("[^a-záéíóúãõâêîôûàèìòùç]", "");
                if (!word.isEmpty()) {
                    inputIds[0][pos] = getTokenId(word);
                    attentionMask[0][pos] = 1;
                    pos++;
                }
            }

            // Token [SEP] no final
            if (pos < maxLength) {
                inputIds[0][pos] = 102;  // ID do [SEP]
                attentionMask[0][pos] = 1;
            }
        }

        // Resultado
        Map<String, Object> result = new HashMap<>();
        result.put("input_ids", inputIds);
        result.put("attention_mask", attentionMask);

        return result;
    }

    private long getTokenId(String word) {
        // Mapeamento simples para teste
        Map<String, Long> vocab = new HashMap<>();
        vocab.put("adorei", 1000L);
        vocab.put("gostei", 1001L);
        vocab.put("excelente", 1002L);
        vocab.put("bom", 1003L);
        vocab.put("ótimo", 1004L);
        vocab.put("ruim", 2000L);
        vocab.put("péssimo", 2001L);
        vocab.put("odiei", 2002L);
        vocab.put("horrível", 2003L);

        return vocab.getOrDefault(word, 100L); // [UNK] para desconhecidos
    }
}
