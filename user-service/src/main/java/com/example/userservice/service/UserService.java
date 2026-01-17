package com.example.userservice.service;

import com.example.userservice.DTO.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public User createUser(UserDto userDto){

        User user=modelMapper.map(userDto,User.class);
        user=userRepository.save(user);

        return user;
    }

    public List<User> createBulkUser(List<UserDto> userDtos){

        List<User> orders = userDtos.stream()
                .map(dto -> {
                    User user = modelMapper.map(dto, User.class);
                    return user;
                })
                .toList();

        return userRepository.saveAll(orders);
    }


    public User getByUserId(Long id){
        Optional<User> user=userRepository.findById(id);
        return user.orElse(null);
    }


    public List<User> getUserList(int page,int size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<User> userPage=userRepository.findAll(pageable);
        return userPage.getContent();
    }

}
