package state;

import token.*;

import java.io.IOException;

public class StartState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) throws IOException {
        char c = tokenizer.getCurChar();
        tokenizer.nextChar();
        return switch (c) {
            case '+' -> new Plus();
            case '-' -> new Minus();
            case '*' -> new Multiply();
            case '/' -> new Divide();
            case '(' -> new OpenBrace();
            case ')' -> new CloseBrace();
            default -> null;
        };
    }

    @Override
    public void next(Tokenizer tokenizer) {
        if (tokenizer.isOperationOrBrace()) {
            tokenizer.setState(new StartState());
        } else if (tokenizer.isDigit()) {
            tokenizer.setState(new NumberState());
        } else if (tokenizer.isEOF()) {
            tokenizer.setState(new EndState());
        } else {
            tokenizer.setState(new ErrorState("Expected operation|brace|number|EOF, but found: " + tokenizer.getCurChar()));
        }
    }
}
