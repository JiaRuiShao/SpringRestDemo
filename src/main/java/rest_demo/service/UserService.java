package rest_demo.service;

import java.util.List;

import rest_demo.dto.PagedResponse;
import rest_demo.dto.User;

public interface UserService {

		User findById(long id);

		User saveUser(User user);

		User updateUser(User user);

		void deleteUserById(long id);
	 
	    List<User> findAllUsers();

		PagedResponse<User> findPaginated(int page, int size, String orderBy);
	     
	    
}