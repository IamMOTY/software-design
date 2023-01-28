package token;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    public void testSingleToken() throws IOException, ParseException {
        assertEquals(List.of(new Number(35)), tokens("35"));
        assertEquals(List.of(new Minus()), tokens("-"));
        assertEquals(List.of(new CloseBrace()), tokens(")"));
    }

    @Test
    public void testBinaryOperation() throws IOException, ParseException {
        assertEquals(List.of(new Number(1), new Plus(), new Number(2)), tokens("1 + 2"));
        assertEquals(List.of(new Number(350), new Divide(), new Number(20)), tokens("350 / 20"));
    }

    @Test
    public void testBraces() throws IOException, ParseException {
        assertEquals(List.of(
                        new OpenBrace(),
                        new Number(30),
                        new Plus(),
                        new Number(2),
                        new CloseBrace(),
                        new Divide(),
                        new Number(8)),
                tokens("(30 + 2) / 8"));
    }

    @Test
    public void testWhitespace() throws IOException, ParseException {
        assertEquals(List.of(new OpenBrace(),
                        new Number(1),
                        new Plus(),
                        new Number(2),
                        new CloseBrace(),
                        new Divide(),
                        new Number(30)),
                tokens("(    1 +     2) /     30"));
    }

    @Test
    public void testError() {
        assertThrows(ParseException.class, () -> tokens("1 2"));
        assertThrows(ParseException.class, () -> tokens("1 plus 2"));
    }

    private List<Token> tokens(String input) throws IOException, ParseException {
        return new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))).tokenize();
    }

}
