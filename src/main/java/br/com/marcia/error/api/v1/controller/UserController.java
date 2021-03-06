package br.com.marcia.error.api.v1.controller;

import br.com.marcia.error.api.v1.dto.request.UserRequestDTO;
import br.com.marcia.error.api.v1.dto.response.UserResponseDTO;
import br.com.marcia.error.entity.UserEntity;
import br.com.marcia.error.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"user"})
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created")
    })
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO userRequestDTO) {

        UserEntity userEntity = modelMapper.map(userRequestDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(userService.save(userEntity), UserResponseDTO.class));
    }

}
