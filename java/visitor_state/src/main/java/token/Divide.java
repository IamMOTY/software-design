package token;

public class Divide extends Operation {
    @Override
    public TokenType getTokenType() {
        return TokenType.DIVIDE;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public Number apply(Number a, Number b) {
        return new Number(a.value() / b.value());
    }

    @Override
    public String toString() {
        return "/";
    }
}
