package common.utils;

import java.util.Optional;

import static play.mvc.Controller.session;

public class Session {

    public Optional<String> readString(final String key) {
        return Optional.ofNullable(session(key));
    }

    public static Optional<Integer> readInt(final String key) {
        try {
            return Optional.of(Integer.parseInt(session(key)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
