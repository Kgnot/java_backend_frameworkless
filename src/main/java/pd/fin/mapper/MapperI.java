package pd.fin.mapper;

import pd.fin.core.User;
import pd.fin.dto.UserDto;
import pd.fin.entities.UserEntity;

import java.util.List;
// con mapstruct solo se haría:
// @Mapper
public interface MapperI {

    static List<User> entitiesToCores(List<UserEntity> users) {
        return users.stream().map(MapperI::entityToCore).toList();
    }

    // Aqui podemos usar Map struct | pero no lo tenemos en la dependencia
    static User entityToCore(UserEntity entity) {
        return new User(
                User.UserId.of(entity.id()),
                User.Username.of(entity.username()),
                User.Password.of(entity.password()),
                User.FirstName.of(entity.firstName()),
                User.LastName.of(entity.lastName()));
    }

    static List<UserDto> coreToDtos(List<User> users) {
        return users.stream().map(MapperI::coreToDto).toList();
    }

    static UserDto coreToDto(User user) {
        return new UserDto(
                user.userId().value(),
                user.firstName().value(),
                user.lastName().value());
    }
}
