package com.menezes.neto.dreamshops.service.user;

import com.menezes.neto.dreamshops.dto.UserDTO;
import com.menezes.neto.dreamshops.model.User;
import com.menezes.neto.dreamshops.request.AddUserRequest;
import com.menezes.neto.dreamshops.request.UpdateUserRequest;

public interface IUserService {
    User getById(Long id);
    User add(AddUserRequest request);
    User update(UpdateUserRequest request, Long id);
    void delete(Long id);

    UserDTO convertUserToDto(User user);
}
