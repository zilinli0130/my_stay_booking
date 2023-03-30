//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of StayController class.
//**********************************************************************************************************************

package com.zilinli.staybooking.controller;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.model.Stay;
import com.zilinli.staybooking.model.User;
import com.zilinli.staybooking.service.StayService;

// Framework includes
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// System includes
import java.util.List;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@RestController
public class StayController {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************

    public StayController(StayService stayService) {
        this.stayService = stayService;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    @GetMapping(value = "/stays")
    public List<Stay> listStays(@RequestParam(name = "host") String hostName) {
        return stayService.listByUser(hostName);
    }

    @GetMapping(value = "/stays/id")
    public Stay getStay(@RequestParam(name = "stay_id") Long stayId, @RequestParam(name = "host") String hostName) {
        return stayService.findByIdAndHost(stayId, hostName);
    }

    @PostMapping("/stays")
    public void addStay(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("description") String description,
            @RequestParam("host") String host,
            @RequestParam("guest_number") int guestNumber,
            @RequestParam("images") MultipartFile[] images) {

        Stay stay = new Stay.Builder().setName(name)
                .setAddress(address)
                .setDescription(description)
                .setGuestNumber(guestNumber)
                .setHost(new User.Builder().setUsername(host).build())
                .build();
        stayService.addStay(stay, images);
    }

    @DeleteMapping("/stays")
    public void deleteStay(@RequestParam(name = "stay_id") Long stayId, @RequestParam(name = "host") String hostName) {
        stayService.delete(stayId, hostName);
    }

//**********************************************************************************************************************
// * Protected methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    private final StayService stayService;
}