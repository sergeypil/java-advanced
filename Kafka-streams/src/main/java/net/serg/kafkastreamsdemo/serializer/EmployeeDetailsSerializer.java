package net.serg.kafkastreamsdemo.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.serg.kafkastreamsdemo.model.EmployeeDetails;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class EmployeeDetailsSerializer implements Serializer<EmployeeDetails> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String s, EmployeeDetails employeeDetails) {
        try {
            return objectMapper.writeValueAsBytes(employeeDetails);
        } catch (Exception e) {
            throw new SerializationException("Error serializing value", e);
        }
    }

    @Override
    public void close() {
    }
}

