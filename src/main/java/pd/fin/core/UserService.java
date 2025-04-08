package pd.fin.core;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class UserService {

    // Entonces en el servicio se mostrarán los métodos que quiero usar:
    private final Supplier<List<User>> getAllUsers;
    private final Function<User.UserId, User> getUserById;


    public UserService(Supplier<List<User>> getAllUsers,
                       Function<User.UserId, User> getUserById) {
        this.getAllUsers = getAllUsers;
        this.getUserById = getUserById;
    }

    public List<User> getAllUsers() {
        return getAllUsers.get();
    }

    public Optional<User> getUserById(User.UserId userId) {
        return Optional.of(getUserById.apply(userId));
    }


}
