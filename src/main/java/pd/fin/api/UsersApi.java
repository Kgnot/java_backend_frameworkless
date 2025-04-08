package pd.fin.api;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pd.fin.dto.UserDto;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


@WebServlet(value = "/api/users/*")
public class UsersApi extends HttpServlet {

    private final Function<UserDto,String> mapUserToJson;
    private final Function<List<UserDto>,String> mapUserListToJson;
    // Estos son los métodos, podemos implementarlos de post y mas xd
    private final Supplier<List<UserDto>> getAllUsers;
    private final Function<Integer, UserDto> getUser;

    public UsersApi(Function<UserDto, String> mapUserToJson,
                    Function<List<UserDto>, String> mapUserListToJson,
                    Supplier<List<UserDto>> getAllUsers,
                    Function<Integer, UserDto> getUser) {
        this.mapUserToJson = mapUserToJson;
        this.mapUserListToJson = mapUserListToJson;
        this.getAllUsers = getAllUsers;
        this.getUser = getUser;
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        var pathInfo = req.getPathInfo();
        try{
            if(pathInfo == null || pathInfo.equals("/")) {
                var users = getAllUsers.get();
                var json = mapUserListToJson.apply(users);
                resp.getWriter().write(json);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            else{
                var id = Integer.parseInt(pathInfo.substring(1));
                var user = getUser.apply(id);
                if(user == null){
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                else{
                    var json = mapUserToJson.apply(user);
                    resp.getWriter().write(json);
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
            }
        }
        catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Error: " + e.getMessage());
        }
        finally {
            resp.getWriter().flush();
            resp.getWriter().close();
        }

    }

}
