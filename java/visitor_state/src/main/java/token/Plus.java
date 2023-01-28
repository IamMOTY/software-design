package token;

public class Plus extends Operation {
    @Override
    public TokenType getTokenType() {
        return TokenType.PLUS;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public Number apply(Number a, Number b) {
        return new Number(a.value() + b.value());
    }

    @Override
    public String toString() {
        return "+";
    }
}
