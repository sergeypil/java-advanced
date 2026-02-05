package net.serg.kafkastreamsdemo.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.serg.kafkastreamsdemo.model.EmployeeDetails;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class EmployeeDetailsDeserializer implements Deserializer<EmployeeDetails> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public EmployeeDetails deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, EmployeeDetails.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing value", e);
        }
    }

    @Override
    public void close() {
    }
}