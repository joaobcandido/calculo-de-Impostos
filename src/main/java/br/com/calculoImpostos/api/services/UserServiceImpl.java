package br.com.calculoImpostos.api.services;


import br.com.calculoImpostos.api.dtos.RegisterUserDto;
import br.com.calculoImpostos.api.models.RoleEntity;
import br.com.calculoImpostos.api.models.UserEntity;
import br.com.calculoImpostos.api.repositories.RoleRepository;
import br.com.calculoImpostos.api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerUser(RegisterUserDto registerUserDto) {
        if (userRepository.existsByUsername(registerUserDto.username())) {
            throw new RuntimeException("Unprocess Entity");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerUserDto.username());
        userEntity.setPassword(passwordEncoder.encode(registerUserDto.password()));

        Set<RoleEntity> roleEntities = new HashSet<>();
        String roleName = "ROLE_" + registerUserDto.role().toUpperCase(); // Adiciona o prefixo ROLE_
        RoleEntity roleEntity = roleRepository.findByName(roleName);
        if (roleEntity == null) {
            roleEntity = new RoleEntity(roleName);
            roleRepository.save(roleEntity);
        }

        roleEntities.add(roleEntity);
        userEntity.setRoleEntities(roleEntities);
        userRepository.save(userEntity);
    }
}


