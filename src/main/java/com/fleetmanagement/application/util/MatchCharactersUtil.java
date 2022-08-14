package com.fleetmanagement.application.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MatchCharactersUtil {

    private static final String BAG_REGEX = "[\\d|^C]";
    private static final String PACKAGE_REGEX = "[\\d|^P]";

    private static boolean hasMatchCharacters(String regex, String input){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static List<String> getBagBarcodes(List<String> barcodes) {
        return barcodes.stream().filter(barcode -> hasMatchCharacters(BAG_REGEX, barcode))
            .collect(Collectors.toList());
    }

    public static List<String> getPackageBarcodes(List<String> barcodes) {
        return barcodes.stream().filter(barcode -> hasMatchCharacters(PACKAGE_REGEX, barcode))
            .collect(Collectors.toList());
    }

}
