package com.powsybl.commons.reporter;

import org.apache.commons.text.StringSubstitutor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractReportNode implements ReportNode {

    private final String key;
    private final String defaultText;
    private final Map<String, TypedValue> values;

    protected AbstractReportNode(String key, String defaultText, Map<String, TypedValue> values) {
        this.key = key;
        this.defaultText = defaultText;
        this.values = new HashMap<>();
        Objects.requireNonNull(values).forEach(this::addValue);
    }

    private void addValue(String key, TypedValue typedValue) {
        Objects.requireNonNull(typedValue);
        values.put(key, typedValue);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDefaultText() {
        return defaultText;
    }

    @Override
    public Map<String, TypedValue> getValues() {
        return Collections.unmodifiableMap(values);
    }

    @Override
    public TypedValue getValue(String valueKey) {
        return values.get(valueKey);
    }

    /**
     * Format given message by replacing value references by the corresponding values.
     * The values in the given message have to be referred to with their corresponding key, using the <code>${key}</code> syntax.
     * {@link org.apache.commons.text.StringSubstitutor} is used for the string replacements.
     * @param message the message to be formatted
     * @param values the key-value map used to look for the values
     * @return the resulting formatted string
     */
    protected static String formatMessage(String message, Map<String, TypedValue> values) {
        return new StringSubstitutor(values).replace(message);
    }
}
