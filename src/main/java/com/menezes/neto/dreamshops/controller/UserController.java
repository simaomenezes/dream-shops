package com.menezes.neto.dreamshops.controller;

import com.menezes.neto.dreamshops.dto.UserDTO;
import com.menezes.neto.dreamshops.exceptions.AlreadyExistsException;
import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.User;
import com.menezes.neto.dreamshops.request.AddUserRequest;
import com.menezes.neto.dreamshops.request.UpdateUserRequest;
import com.menezes.neto.dreamshops.response.ApiResponse;
import com.menezes.neto.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService service;

    @GetMapping("/{id}/user")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id){
        try {
            User user = service.getById(id);
            UserDTO userDTO = service.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success!", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody AddUserRequest request){
        try {
            User userCreated = service.add(request);
            UserDTO userDTO = service.convertUserToDto(userCreated);
            return ResponseEntity.ok(new ApiResponse("success", userDTO));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateUserRequest request, @PathVariable Long id){
        try {
            User userFounded = service.update(request, id);
            UserDTO userDTO = service.convertUserToDto(userFounded);
            return ResponseEntity.ok(new ApiResponse("Success", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        try {
            service.delete(id);
            return ResponseEntity.ok(new ApiResponse("User success delete with id: ", id ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
