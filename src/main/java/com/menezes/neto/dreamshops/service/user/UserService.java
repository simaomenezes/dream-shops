package com.menezes.neto.dreamshops.service.user;

import com.menezes.neto.dreamshops.exceptions.AlreadyExistsException;
import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.User;
import com.menezes.neto.dreamshops.repository.UserRepository;
import com.menezes.neto.dreamshops.request.AddUserRequest;
import com.menezes.neto.dreamshops.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private UserRepository repository;
    @Override
    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User add(AddUserRequest request) {
        return Optional.of(request)
                .filter(user -> !repository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPassword(request.getPassword());
                    return repository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops!" +request.getEmail() +" already exists!"));
    }

    @Override
    public User update(UpdateUserRequest request, Long id) {
        return repository.findById(id).map(foundUser -> {
            foundUser.setFirstName(request.getFirstName());
            foundUser.setLastName(request.getLastName());
            return repository.save(foundUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void delete(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () ->{
            throw new ResourceNotFoundException("User not found!");
        });
    }
}
