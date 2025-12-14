package com.fedbacknow.fedbacknow.dto;


import com.fedbacknow.fedbacknow.validation.NotAllUppercase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record SentimentRequestDTO(
        @NotBlank(message = "A reclamação, sugestão ou elogios é obrigatória")
        @Size(min = 5, max = 500, message = "A informação deve ter entre 5 e 500 caracteres")
        @Pattern(
                regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Zs}]+$",
                message = "A mensagem não pode conter emojis ou ícones!"
        )
        @NotAllUppercase
        String text,
        @NotNull(message = "O produto é obrigatorio")
        String productName) {
}
