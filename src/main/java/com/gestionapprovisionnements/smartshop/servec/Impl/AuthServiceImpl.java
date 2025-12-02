package com.gestionapprovisionnements.smartshop.servec.Impl;

import com.gestionapprovisionnements.smartshop.dto.Auth.Request.LoginRequest;
import com.gestionapprovisionnements.smartshop.dto.User.Request.UserRequest;
import com.gestionapprovisionnements.smartshop.dto.User.Response.UserResponse;
import com.gestionapprovisionnements.smartshop.entity.Client;
import com.gestionapprovisionnements.smartshop.entity.User;
import com.gestionapprovisionnements.smartshop.entity.enums.CustomerTier;
import com.gestionapprovisionnements.smartshop.entity.enums.Role;
import com.gestionapprovisionnements.smartshop.entity.enums.UserRole;
import com.gestionapprovisionnements.smartshop.exiption.IncorrectPasswordException;
import com.gestionapprovisionnements.smartshop.exiption.NotFoundException;
import com.gestionapprovisionnements.smartshop.exiption.ResourceAlreadyExistsException;
import com.gestionapprovisionnements.smartshop.mapper.UserMapper;
import com.gestionapprovisionnements.smartshop.repository.ClientRepository;
import com.gestionapprovisionnements.smartshop.repository.UserRepository;
import com.gestionapprovisionnements.smartshop.servec.AuthService;
import com.gestionapprovisionnements.smartshop.utils.UtliPassword;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ClientRepository clientRepository ;
    private  final UserRepository userRepository ;
    private  final  UserMapper userMapper ;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        if(userRepository.existsByUsername(userRequest.getUsername())){
            throw new ResourceAlreadyExistsException("User with this email already exists");
        }
        User user = userMapper.toEntity(userRequest) ;
        user.setPassword(UtliPassword.hashPassword(userRequest.getPassword()));
        User saveUser = userRepository.save(user) ;
        if(userRequest.getRole() == UserRole.CLIENT){
            Client client = new Client() ;
            client.setUser(saveUser);
            client.setNom(saveUser.getUsername());
            client.setEmail(userRequest.getEmail());
            client.setCustomerTier(CustomerTier.BASIC);
            clientRepository.save(client) ;
        }
        return userMapper.toDto(saveUser) ;
    }

    @Override
    public UserResponse login(LoginRequest loginRequest , HttpSession httpSession){
        //check email is exist in databae
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new NotFoundException("user with name not found")) ;
        if(!UtliPassword.verifyPassword( loginRequest.getPassword() , user.getPassword() )){
                 throw new IncorrectPasswordException("invalid password") ;
        }
        httpSession.setAttribute("userId" , user.getId());
        httpSession.setAttribute("userName" , user.getUsername());
        httpSession.setAttribute("userRole" , user.getRole());

        return userMapper.toDto(user) ;


    }

    @Override
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }


}
