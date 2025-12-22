package com.fedbacknow.fedbacknow.config;


import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.InputStream;


    @Configuration
    public class OnnxConfig {

        @Value("classpath:model/model.onnx")  // Injeta o arquivo do modelo
        private Resource modelResource;

        @Bean
        public OrtSession loadModel() throws Exception {
            OrtEnvironment env = OrtEnvironment.getEnvironment(); // Cria ambiente ONNX
            try (InputStream modelStream = modelResource.getInputStream()){
                byte[] modelBytes = modelStream.readAllBytes(); // Lê modelo para memória
                return env.createSession(modelBytes); // Cria sessão de inferência
            }
        }

        @Bean
        public OrtEnvironment ortEnvironment() {
            return OrtEnvironment.getEnvironment(); // Bean para injeção de dependência
        }
}
