package com.myprogect.mywarehouse.web.controller;

import com.myprogect.mywarehouse.service.WarehouseUsersService;
import com.myprogect.mywarehouse.db.entity.UserRole;
import com.myprogect.mywarehouse.service.dto.WarehouseUserDTO;
import com.myprogect.mywarehouse.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import static com.myprogect.mywarehouse.web.constant.Constants.BaseController.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@Secured({"ROLE_ADMIN"})
@RequestMapping(USERS)
public class UserController {
    @Autowired
    WarehouseUsersService usersService;
    @Autowired
    UserValidator validator;


    @RequestMapping(value = LISTING)
    public String showAllUsers(Model model){
        WarehouseUserDTO addUser = new WarehouseUserDTO();
        List<WarehouseUserDTO> findAllUsers = usersService.findAllUsers();
        UserRole[] role = UserRole.values();
        model.addAttribute("usersList",findAllUsers);
        model.addAttribute("userRole", role);
        model.addAttribute("userAdd", addUser);
        return "users_page";
    }

    @RequestMapping(value = DELETE + "{id}")
    public String recordDelete(@PathVariable("id") Long id){
        if(id == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find id");
        usersService.deleteDate(id);
        return "redirect:" + USERS + LISTING;
    }

    @RequestMapping(value = ADD, method = RequestMethod.POST)
    public String addUserToDB(@Valid @ModelAttribute("userAdd") WarehouseUserDTO userFromPage,
                              BindingResult bindingResult, Model model){
        WarehouseUserDTO userToDB = newUserDTO(userFromPage);

        validator.validate(userToDB,bindingResult);

        if(bindingResult.hasErrors()){
            List<WarehouseUserDTO> findAllUsers = usersService.findAllUsers();
            UserRole[] role = UserRole.values();
            model.addAttribute("usersList",findAllUsers);
            model.addAttribute("userRole", role);
            return "users_page";
        }
        usersService.saveData(userToDB);

        return "redirect:" + USERS + LISTING;
    }

    private WarehouseUserDTO newUserDTO(WarehouseUserDTO user){
        return new WarehouseUserDTO()
                .setUserName(user.getUserName())
                .setUserPassword(user.getUserPassword())
                .setUserRole(user.getUserRole());
    }
}
