package com.gooddata.project.outputstage;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.trimToEmpty;
import static org.apache.commons.lang.Validate.notEmpty;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description of warehouse column data type
 */
public class DataType {

    private static final Pattern DATA_TYPE_PATTERN = Pattern.compile("([a-z]+)(?:\\s*\\(\\s*(\\d+)(?:\\s*,\\s*(\\d+))?\\s*\\))?", Pattern.CASE_INSENSITIVE);

    private final DataTypeName typeName;

    private final Integer length;

    private final Integer decimals;

    /**
     * For deserialization only
     */
    @JsonCreator
    protected DataType(String serialized) {
        notEmpty(serialized, "serialized cannot be null");

        final Matcher m = DATA_TYPE_PATTERN.matcher(trimToEmpty(serialized));
        if (!m.matches()) {
            throw new IllegalArgumentException(format("'%s' is not a valid data type definition.", serialized));
        }

        final String type = m.group(1);
        final Integer length = m.group(2) == null ? null : Integer.parseInt(m.group(2));
        final Integer decimals = m.group(3) == null ? null : Integer.parseInt(m.group(3));

        try {
            this.typeName = DataTypeName.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(format("'%s' is not a supported data type; expected one of %s",
                    type, Arrays.toString(DataTypeName.values())), e);
        }
        this.length = length;
        this.decimals = decimals;
    }

    public DataType(DataTypeName typeName) {
        this(typeName, null);
    }

    public DataType(DataTypeName typeName, Integer length) {
        this(typeName, length, null);
    }

    public DataType(DataTypeName typeName, Integer length, Integer decimals) {
        this.typeName = typeName;
        this.length = typeName.hasLength() ? length : null;
        this.decimals = typeName.hasDecimals() ? decimals : null;
    }

    public DataTypeName getTypeName() {
        return typeName;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getDecimals() {
        return decimals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DataType that = (DataType) o;

        if (decimals != null ? !decimals.equals(that.decimals) : that.decimals != null) {
            return false;
        }
        if (length != null ? !length.equals(that.length) : that.length != null) {
            return false;
        }
        if (typeName != that.typeName) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeName != null ? typeName.hashCode() : 0;
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (decimals != null ? decimals.hashCode() : 0);
        return result;
    }

    @Override
    @JsonValue
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(typeName);
        if (length != null) {
            sb.append("(").append(length);
            if (decimals != null) {
                sb.append(",");
                sb.append(decimals);
            }
            sb.append(")");
        }
        return sb.toString();
    }
}
