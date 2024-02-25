package net.serg.secureapplication.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.serg.secureapplication.entity.User;
import net.serg.secureapplication.service.UserService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
    private final UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
        throws IOException, ServletException {
        String username = request.getParameter("username");
        User user = userService.findByUsername(username);
        if(user != null) {
            user.setLoginAttempt(user.getLoginAttempt() + 1);
            if(user.getLoginAttempt() >= 3) {
                user.setLockTime(LocalDateTime.now());
            }
            userService.save(user);
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}