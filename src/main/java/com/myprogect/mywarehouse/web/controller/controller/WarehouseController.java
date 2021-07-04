package com.myprogect.mywarehouse.web.controller.controller;

import com.myprogect.mywarehouse.service.WarehouseService;
import com.myprogect.mywarehouse.service.dto.WarehouseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import static com.myprogect.mywarehouse.web.controller.constant.Constants.BaseController.LISTING;
import static com.myprogect.mywarehouse.web.controller.constant.Constants.BaseController.WAREHOUSE;

@Controller
@RequestMapping(WAREHOUSE)
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;

    @RequestMapping(value = LISTING)
    public String showAllProducts(Model model){
        List<WarehouseDTO> findAllRecords = warehouseService.findTotalSumQuantity();
        model.addAttribute("warehouseList",findAllRecords);
        return "warehouse_page";
    }
}
