package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.WarehouseUserDTO;
import java.util.List;

public interface WarehouseUsersService {
    WarehouseUserDTO byId(Long id);
    List<WarehouseUserDTO> findAllUsers();
    void saveData(WarehouseUserDTO newUser);
    void deleteDate(Long id);
    WarehouseUserDTO findByUserName(String userName);
    WarehouseUserDTO findByUserPassword(String userPassword);
}
