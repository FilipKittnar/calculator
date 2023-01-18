package cz.assist.axon_training.bff.calculator.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CalculatorConstants {

    public final String FETCH_CALCULATOR_URL = "/calculator/get";
    public final String PERFORM_CALCULATE_URL = "/calculator/perform-calculate";
    public final String CLEAR_MEMORY_URL = "/calculator/memory/clear";
    public final String CLEAR_HISTORY_URL = "/calculator/history/clear";
    public final String REMOVE_HISTORY_ROW_URL = "/calculator/history/remove";
    public final String CLEAR_ENTRY_URL = "/calculator/entry/clear";
    public final String ADD_NUMBER_TO_ENTRY_URL = "/calculator/entry/add/number";
    public final String ADD_DECIMAL_SEPARATOR_TO_ENTRY_URL = "/calculator/entry/add/decimal-separator";
    public final String REMOVE_LAST_CHARACTER_FROM_ENTRY_URL = "/calculator/entry/remove-last-character";
    public final String SELECT_OPERATOR_URL = "/calculator/operator/select";

}
