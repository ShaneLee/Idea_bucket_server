package ee.shanel.ideabucket.cucumber;

import java.lang.reflect.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CucumberConfiguration
{
    private final ObjectMapper objectMapper;

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object defaultTransformer(final Object fromValue, final Type toValueType)
    {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }
}
