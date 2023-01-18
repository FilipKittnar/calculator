package cz.assist.axon_training.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

@UtilityClass
public class NumberUtils {

    public final String DECIMAL_SEPARATOR = ".";

    public Optional<BigDecimal> getNormalizedNumber(Optional<String> value) {
        return value.map(e -> {
            var normalized = StringUtils.hasText(e) && DECIMAL_SEPARATOR.equals(e.substring(e.length() - 1))
                ? e.substring(0, e.length() - 1)
                : e;
            return StringUtils.hasText(normalized) ? new BigDecimal(normalized).stripTrailingZeros() : null;
        });
    }
}
