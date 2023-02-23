package com.pat.SpellsOfEnglish.security;

import com.pat.SpellsOfEnglish.data.entity.User;
import com.pat.SpellsOfEnglish.data.repository.UserRepository;
import com.pat.SpellsOfEnglish.service.Impl.TotpManager;
import com.pat.SpellsOfEnglish.service.exception.NotFoundException;
import com.pat.SpellsOfEnglish.service.exception.SoeException;
import com.pat.SpellsOfEnglish.service.plugin.InternalizationMessageManagerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    private UserRepository userRepository;
    private TotpManager totpManager;
    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";
    public static final String INVALID_VERIFICATION_CODE = "Invalid verification code";
    public static final String KEY_FOR_EXCEPTION_USER_NOT_FOUND = "UserService.UserNotFound";

    @Override
    public Authentication authenticate(Authentication auth) {
        String verificationCode = ((CustomWebAuthenticationDetails) auth.getDetails())
                .getVerificationCode();
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        if ((user == null)) {
            throw new SoeException(INVALID_USERNAME_OR_PASSWORD);
        }
        if (user.isUsing2FA()) {
            if (!totpManager.verifyCode(verificationCode, user.getSecret())) {
                throw new SoeException(INVALID_VERIFICATION_CODE);
            }
        }

        Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(
                user, result.getCredentials(), result.getAuthorities()); //FixMe getCredentials
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
