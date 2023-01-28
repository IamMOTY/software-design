package state;

import token.Number;
import token.Token;
import token.Tokenizer;

import java.io.IOException;

public class NumberState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) throws IOException {
        return new Number(tokenizer.parseNumber());
    }

    @Override
    public void next(Tokenizer tokenizer) {
        if (tokenizer.isOperationOrBrace()) {
            tokenizer.setState(new StartState());
        } else if (tokenizer.isEOF()) {
            tokenizer.setState(new EndState());
        } else {
            tokenizer.setState(new ErrorState("Expected operation|brace|EOF, but found: " + tokenizer.getCurChar()));
        }
    }
}
