package net.serg.kafkastreamsdemo.serializer;

import net.serg.kafkastreamsdemo.model.EmployeeDetails;
import org.apache.kafka.common.serialization.Serdes;

public class CustomSerdes {
    static public final class EmployeeDetailsSerde
        extends Serdes.WrapperSerde<EmployeeDetails> {
        public EmployeeDetailsSerde() {
            super(new EmployeeDetailsSerializer(), new EmployeeDetailsDeserializer());
        }
    }
}