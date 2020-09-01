package xyz.codepunk.phpbbparser.common;

import xyz.codepunk.phpbbparser.exceptions.TranslationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class BaseParser {
    final static HashMap<String, ArrayList<String>> translations = new HashMap<>() {{
        put("joined", new ArrayList(Arrays.asList("iscrizione")));
        put("posts", new ArrayList(Arrays.asList("messaggi")));
    }};

    /**
     * Return hashmap of URL parameters.
     * @param urlStr the URL
     * @return HashMap containing parameters and their values
     */
    public static HashMap<String, String> parseUrlParams(String urlStr) {
        final String queryStr = urlStr.split("\\?")[1];
        final String[] paramsList = queryStr.split("\\&");
        final HashMap<String, String> params = new HashMap<String, String>();
        for(String paramString : paramsList) {
            final String[] tokens = paramString.split("=");
            final String key = tokens[0];
            String val = "";
            for(int i=1; i < tokens.length; i++) {
                val += tokens[i];
            }
            params.put(key, val);
        }
        return params;
    }

    /**
     * Return URL parameter value for `key`
     * @param urlStr the URL
     * @param key the parameter to return
     * @return the parameter value as a String
     */
    public static String parseUrlParam(String urlStr, String key) {
        final HashMap<String, String> params = parseUrlParams(urlStr);
        return params.get(key);
    }

    /**
     * Search text for substring or matching translation
     *
     * @param text the text to search
     * @param substring
     * @return substring the text to search for
     * @throws TranslationException if there is no translation available
     */
    public static Boolean containsTextOrTranslation(String text, String substring) throws TranslationException {
        final ArrayList<String> substrings = new ArrayList<>();
        substrings.add(substring);
        try {
            for(String altText: translations.get(substring.toLowerCase())) {
                substrings.add(altText);
            }
        } catch(NullPointerException e) {
            throw new TranslationException("No translation for '" + substring + "'");
        }

        for(String matchText: substrings) {
            if(text.toLowerCase().contains(matchText)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Replace text or matching translation with replacement text.
     *
     * @param text the text on which to perform the replacement
     * @param substring the substring to replace
     * @param replacement the replacement string
     * @return the altered text
     * @throws TranslationException if no translation exists
     */
    public static String replaceTextOrTranslation(String text, String substring, String replacement) throws TranslationException {
        final ArrayList<String> substrings = new ArrayList<>();
        substrings.add(substring);
        try {
            for(String translation: translations.get(substring.toLowerCase())) {
                substrings.add(translation);
            }
        } catch(NullPointerException e) {
            throw new TranslationException("No translation for '" + substring + "'");
        }
        String newText = text;
        for(String matchText: substrings) {
            newText = newText.replaceAll("(?i)"+ Pattern.quote(matchText), replacement);
        }
        return newText;
    }

    /**
     * Split string by text or translation.
     *
     * @param text the string to split
     * @param split the text pattern upon which to split
     * @return tokenized string as String[]
     * @throws TranslationException if no translation exists
     */
    public static String[] splitByTextOrTranslation(String text, String split) throws TranslationException {
        final ArrayList<String> splitCandidates = new ArrayList<>();
        splitCandidates.add(split);
        String[] splitText = {text};
        try {
            for(String translation: translations.get(split.toLowerCase())) {
                splitCandidates.add(translation);
            }
        } catch(NullPointerException e) {
            throw new TranslationException("No translation for '" + split + "'");
        }
        for(String candidate: splitCandidates) {
            if(text.toLowerCase().contains(candidate.toLowerCase())) {
                return text.split("(?i)" + candidate);
            }
        }
        return splitText;
    }
}
