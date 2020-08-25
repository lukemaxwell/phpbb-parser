package xyz.codepunk;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaseParserTest extends TestCase {


    @Test
    public void testParseUrlParams() {
        String urlStr = "/viewtopic.php?f=110&t=30871&start=190";
        HashMap<String, String> params = PHPBBParser.parseUrlParams(urlStr);
        assertTrue(params.containsKey("f") && params.get("f").equals("110"));
        assertTrue(params.containsKey("t") && params.get("t").equals("30871"));
        assertTrue(params.containsKey("start") && params.get("start").equals("190"));
        assertEquals(3, params.keySet().size());
    }

    @Test
    public void testParseUrlParam() {
        String urlStr = "/viewtopic.php?f=110&t=30871&start=190";
        assertEquals("30871", PHPBBParser.parseUrlParam(urlStr, "t"));
    }
}