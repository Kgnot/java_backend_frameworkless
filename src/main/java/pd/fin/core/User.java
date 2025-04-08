package pd.fin.core;

public record User(UserId userId, Username username, Password password, FirstName firstName, LastName lastName) {

    public User {
        if (userId == null || username == null || password == null || firstName == null || lastName == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
    }

    public record UserId(Integer value) {
        public static UserId of(Integer value) {
            return new UserId(value);
        }

        public static UserId of(String value) {
            return new UserId(Integer.parseInt(value));
        }
    }

    public record Username(String value) {

        public static Username of(String value) {
            return new Username(value);
        }
    }

    public record Password(String value) {
        public static Password of(String value){
            if (value == null || value.length() < 5) {
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
