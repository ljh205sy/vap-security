package com.lxg.springboot.controller;

import com.lxg.springboot.entity.Role;
import com.lxg.springboot.entity.User;
import com.lxg.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wh1107066
 */
@Controller
public class HelloWorldRestController {
  private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    //-------------------Retrieve All Users--------------------------------------------------------

    @PreAuthorize("hasRole('role1')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public List<User> listAllUsers() {
        List<User> users = userService.findAll();
        if(users.isEmpty()){
            return null;
        }
        return users;
    }

    @PreAuthorize("hasAuthority('role2')")
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> listAllRoles() {
        List<Role> roles = new ArrayList<>();
       roles.add(new Role());
        return roles;
    }


    @PreAuthorize("hasRole('xxx')")
    @RequestMapping(value = "/nopermision", method = RequestMethod.GET)
    @ResponseBody
    public String nopermision() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication信息: {}", authentication);
        return "permision";
    }


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "hello";
    }
    
    @GetMapping("/user/me")
    @ResponseBody
    public Principal user(OAuth2Authentication principal){
        return principal;
    }
    
    
    //-------------------Retrieve Single User--------------------------------------------------------
      
    /*@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }*/
  
      
      
    //-------------------Create a User--------------------------------------------------------
      
    /*@RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getName());
  
        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
  
        userService.saveUser(user);
  
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }*/
  
      
    //------------------- Update a User --------------------------------------------------------
      
    /*@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);
          
        User currentUser = userService.findById(id);
          
        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());
          
        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }*/
  
    //------------------- Delete a User --------------------------------------------------------
      
    /*@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);
  
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }*/
  
      
    //------------------- Delete All Users --------------------------------------------------------
      
    /*@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");
  
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }*/
  
}