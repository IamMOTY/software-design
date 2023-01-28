package token;

public class CloseBrace extends Brace {
    @Override
    public TokenType getTokenType() {
        return TokenType.CLOSE_BRACE;
    }

    @Override
    public String toString() {
        return ")";
    }
}
