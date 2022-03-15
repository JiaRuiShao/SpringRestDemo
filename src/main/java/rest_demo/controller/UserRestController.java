package rest_demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rest_demo.exception.UserException;
import rest_demo.exception.UserNotFoundException;
import rest_demo.service.UserService;
import rest_demo.util.Constants;
import rest_demo.dto.ErrorResponse;
import rest_demo.dto.PagedResponse;
import rest_demo.dto.ResponseMessage;
import rest_demo.dto.User;

@RestController // = @Controller + @ResponseBody
// all the responses returned by rest controller are not views, just JSON body
@RequestMapping("/api") // root URI
@Api(value = "User", description = "REST API for Users", tags = {"User"})
public class UserRestController {

    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    UserService userService;
    Constants messages;

    @Autowired
    public UserRestController(UserService userService, Constants messages) {
        this.userService = userService;
        this.messages = messages;
    }

    /** By using ResponseEntity, we can customize the HttpStatus & the HttpHeaders **/

    /**
     * retrieve a user
     **/
    @ApiOperation(value = "gets a single user")
    @RequestMapping(value = "/user/{uid}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("uid") long id) throws UserException {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException(messages.getMessage("USER_NOT_FOUND"));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("HeaderName", "HeaderValue");
        return new ResponseEntity<User>(user, headers, HttpStatus.CREATED); // return the user as the body
    }

    

    // swagger doc: http://localhost:8009/swagger-ui.html#/

    /**
     * Get user by using pagination, if no parameters are provided, the first page with 10 records will be returned
     **/
    @ApiOperation(value = "get users accordingly")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<PagedResponse<User>> getUserPagenation(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(required = false, defaultValue = "5") Integer rows,
                                                                 @RequestParam(required = false, defaultValue = "name") String orderBy) {

        PagedResponse<User> users = userService.findPaginated(pageNo, rows, orderBy);
        if (users.isEmpty()) {
            throw new UserNotFoundException(messages.getMessage("USER_NOT_FOUND"));
        }
        return new ResponseEntity<PagedResponse<User>>(users, HttpStatus.OK);
    }

	/** create a user **/
    @ApiOperation(value = "create a user")
    // @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
    /** Use @Valid or @Validated annotation to auto validate the user input, or manually validate it and throw customized exception **/
    @PostMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<ResponseMessage> createUser(@Validated @RequestBody User user, UriComponentsBuilder ucBuilder) {
        User savedUser = userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(messages.getMessage("USER_CREATED"), savedUser), headers, HttpStatus.CREATED);
    }

	/** update a user **/
    @ApiOperation(value = "update a user")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT) // PUT method is idempotent
    public ResponseEntity<User> updateUser(@Validated @PathVariable("id") long id, @RequestBody User user, @RequestHeader("Authorization") String token) {
        User currentUser = userService.findById(id);

        if (currentUser == null) {
            throw new UserNotFoundException(messages.getMessage("USER_NOT_FOUND"));
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

	/** delete a user **/
    @ApiOperation(value = "delete a user")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable("id") long id) {

        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException(messages.getMessage("USER_NOT_FOUND"));
        }
        userService.deleteUserById(id);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(messages.getMessage("USER_DELETED"), user), HttpStatus.OK);
    }

    /** Exception Handler (we usually handle the exception in the controller layer) **/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        logger.error("Controller Error", ex);
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage()).build(),
                HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerUserNotFound(Exception ex) {
        logger.error("Cannot find user");
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage()).build(), HttpStatus.NOT_FOUND); // 404
    }
}
