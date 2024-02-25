package net.serg.secureapplication.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.serg.secureapplication.entity.User;
import net.serg.secureapplication.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
                                                                                                                                        ServletException {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if(user != null) {
            user.setLoginAttempt(0);
            user.setLockTime(null);
            userService.save(user);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}