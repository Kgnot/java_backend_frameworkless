package pd.fin.entities;


import java.util.UUID;

public record UserEntity (UUID id, String username, String password, String firstName, String lastName) {}