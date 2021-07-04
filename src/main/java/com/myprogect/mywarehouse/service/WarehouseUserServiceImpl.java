package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.WarehouseUsers;
import com.myprogect.mywarehouse.db.repository.WarehouseUserRepository;
import com.myprogect.mywarehouse.service.dto.WarehouseUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseUserServiceImpl implements WarehouseUsersService{
    @Autowired
    WarehouseUserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public WarehouseUserDTO byId(Long id) {
        Optional<WarehouseUsers> userFromDB = repository.findById(id);
        return userFromDB.map(value -> new WarehouseUserDTO()
                            .setId(value.getId())
                            .setUserName(value.getUserName())
                            .setUserPassword(value.getUserPassword())
                            .setUserRole(value.getUserRole())).orElse(null);
    }

    @Override
    public List<WarehouseUserDTO> findAllUsers() {
        List<WarehouseUserDTO> userDTOList = new ArrayList<>();
        for(WarehouseUsers user : repository.findAll()){
            userDTOList.add(new WarehouseUserDTO()
                    .setId(user.getId())
                    .setUserName(user.getUserName())
                    .setUserPassword(user.getUserPassword())
                    .setUserRole(user.getUserRole()));
        }

        return userDTOList;
    }

    @Override
    public void saveData(WarehouseUserDTO newUser) {
        WarehouseUsers userToDB = new WarehouseUsers();

        userToDB.setId(newUser.getId())
                .setUserName(newUser.getUserName())
                .setUserPassword(givenPasswordToMD5(newUser.getUserPassword()))
                .setUserRole(newUser.getUserRole())
                .setAccess(true);
        repository.save(userToDB);
    }

    @Override
    public void deleteDate(Long id) {
        repository.deleteById(id);
    }

    @Override
    public WarehouseUserDTO findByUserName(String userName) {
        Optional<WarehouseUsers> usersFromDB = repository.findByUserName(userName);

        return usersFromDB.map(value -> new WarehouseUserDTO()
                .setUserName(value.getUserName()))
                .orElse(new WarehouseUserDTO().setUserName(""));
    }

    @Override
    public WarehouseUserDTO findByUserPassword(String userPassword) {
        Optional<WarehouseUsers> userFromDB = repository.findByUserPassword(userPassword);

        return userFromDB.map(value -> new WarehouseUserDTO()
                .setUserPassword(value.getUserPassword()))
                .orElse(new WarehouseUserDTO().setUserPassword(""));
    }

    private String givenPasswordToMD5(String password){
        byte[] passwordsByte = password.getBytes();
        String md5Hex = DigestUtils.md5DigestAsHex(passwordsByte).toUpperCase();
        return md5Hex;
    }
}
