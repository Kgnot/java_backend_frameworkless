package pd.fin.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import pd.fin.dto.UserDto;

import java.util.List;

// Aquí usamos jackson xd
public class UserSerializer {

    private static final ObjectMapper mapper;
    private static final ObjectWriter userListWriter;
    private static final ObjectWriter userWriter;

    static {
        mapper = new ObjectMapper();
        userListWriter = mapper.writerFor(new UserListTypeReference());
        userWriter = mapper.writerFor(UserDto.class);
    }

    public static String serializerUserList(List<UserDto> userDtoList)
    {
        try {
            return userListWriter.writeValueAsString(userDtoList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String serializerUser(UserDto value)
    {
        try {
            return userWriter.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static class UserListTypeReference extends TypeReference<List<UserDto>> {}
}
