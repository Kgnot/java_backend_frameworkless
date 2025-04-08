package pd.fin.core;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public record User(UserId userId, Username username, Password password, FirstName firstName, LastName lastName) {

    public User {
        if (userId == null || username == null || password == null || firstName == null || lastName == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
    }

    public record UserId(UUID value) {
        public static UserId of() {
            return new UserId(Generators.timeBasedEpochRandomGenerator().generate());
        }

        public static UserId of(UUID value) {
            return new UserId(value);
        }

        public static UserId of(String value) {
            return new UserId(UUID.fromString(value));
        }
    }

    public record Username(String value) {

        public static Username of(String value) {
            return new Username(value);
        }
    }

    public record Password(String value) {
        public static Password of(String value){
            if (value == null || value.length() < 8) {
                throw new IllegalArgumentException("Password must be at least 8 characters long");
            }
            return new Password(value);
        }
    }

    public record FirstName(String value) {
        public static FirstName of(String value) {
            return new FirstName(value);
        }
    }

    public record LastName(String value) {
        public static LastName of(String value) {
            return new LastName(value);
        }
    }
}
