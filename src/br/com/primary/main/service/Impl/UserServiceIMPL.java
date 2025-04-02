package br.com.primary.main.service.Impl;

import java.util.List;

import br.com.primary.main.model.User;
import br.com.primary.main.repository.IUserRepository;
import br.com.primary.main.service.IUserService;
import br.com.primary.main.service.exceptions.*;

import org.springframework.stereotype.Service;

@Service
public class UserServiceIMPL(IUserRepository repository) implements IUserService {
    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(String email) {
        return repository.findById(email).orElseThrow(()->new NotFoundException("User not found"));
    }

    @Override
    public User create(User user) {
        if(user.getEmail().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank()) {
            throw new BusinessException("Empty fields, please fill in all the data.");
        }
        if(repository.existsById(user.getEmail())) {
            throw new BusinessException("User already registered.");
        }
        try {
            return repository.save(user);
        } catch (BusinessException e) {
            throw new BusinessException("User creation process failed");
        }
    }

    @Override
    public boolean delete(String email) {
        try {
            var user = this.findById(email);
            repository.delete(user);
            return true;
        } catch (BusinessException e) {
                throw new BusinessException("User removal process failed");
        }
    }
}
