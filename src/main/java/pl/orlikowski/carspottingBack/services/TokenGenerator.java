package pl.orlikowski.carspottingBack.services;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TokenGenerator {
    private final int leftLimit = 48;
    private final int rightLimit = 122;
    private final Random random;

    public TokenGenerator() {
        this.random = new Random();
    }

    public String generateToken(int size) {
        return random.ints(leftLimit, rightLimit +1).limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
