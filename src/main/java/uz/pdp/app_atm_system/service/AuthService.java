package uz.pdp.app_atm_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.app_atm_system.entity.User;
import uz.pdp.app_atm_system.payload.ApiResponse;
import uz.pdp.app_atm_system.payload.LoginDto;
import uz.pdp.app_atm_system.repository.CardRepository;
import uz.pdp.app_atm_system.repository.UsersRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CardRepository cardRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username+" topilmadi"));
    }

    public UserDetails loadUserByUsernameFromCard(String username){
        return cardRepository.findByCardNumber(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()));
            User user =(User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRole());
            return new ApiResponse("Token",true,token);

        }catch (BadCredentialsException exception){
            return new ApiResponse("parol yoki login xato",false);
        }

    }
}
