package cat.matxos.control.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginResource {

    public static final String LOGGED ="isLogged";

    @Value("${race.psw}")
    private String psw;

    @Value("${race.user}")
    private String user;

    @GetMapping("/login")
    public String ini() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("user") String user, @RequestParam("password") String password, Model model, HttpServletResponse response) {

        try {
            if (user.equals(this.user) && password.equals(psw)) {

                Cookie loginCookie = new Cookie(LOGGED, user);
                loginCookie.setMaxAge(5 * 60); //set expire time to 1000 sec
                response.addCookie(loginCookie);

                return "redirect:/control/home";
            } else {
                return "login";
            }
        } catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie loginCookie = new Cookie(LOGGED, null);
        loginCookie.setMaxAge(0);
        response.addCookie(loginCookie);
        return "login";
    }
}
