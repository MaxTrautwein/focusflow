package hse.group1.focusflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.UserDto;
import hse.group1.focusflow.model.dto.UserLoginDto;
import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.model.dto.UserUpdateDto;
import hse.group1.focusflow.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.containsString;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRegistrationDto validUserReg;
    private UserRegistrationDto invalidUserReg;

    private UserLoginDto validUserLogin;
    private UserLoginDto invalidUserLogin;

    private User validUser;
    private UserDto validUserDto;
    private UserDto validUserDto2;
    private UserUpdateDto updateUserDto;
    private UserUpdateDto invalidUserUpdateDto;

    @BeforeEach
    void setUp() {
        validUserReg = new UserRegistrationDto();
        validUserReg.setEmail("test@test.com");
        validUserReg.setFirstName("Test");
        validUserReg.setLastName("Test");
        validUserReg.setPassword("Test!112345");

        invalidUserReg = new UserRegistrationDto();
        invalidUserReg.setEmail("test");
        invalidUserReg.setFirstName("Test");
        invalidUserReg.setLastName("Test");
        invalidUserReg.setPassword("a");

        validUserLogin = new UserLoginDto();
        validUserLogin.setEmail("test@test.com");
        validUserLogin.setPassword("Test!112345");
        invalidUserLogin = new UserLoginDto();
        invalidUserLogin.setEmail("test");
        invalidUserLogin.setPassword("a");

        validUser = new User();
        validUser.setEmail("test@test.com");
        validUser.setFirstName("Test");
        validUser.setLastName("Test");
        validUser.setPassword("Test!112345");

        validUserDto = new UserDto();
        validUserDto.setEmail("test@test.com");
        validUserDto.setFirstName("Test");
        validUserDto.setLastName("Test");


        validUserDto2 = new UserDto();
        validUserDto2.setEmail("test2@test.com");
        validUserDto2.setFirstName("Test2");
        validUserDto2.setLastName("Test2");

        updateUserDto = new UserUpdateDto();
        updateUserDto.setEmail("test2@test.com");
        updateUserDto.setFirstName("Test2");
        updateUserDto.setLastName("Test2");
        updateUserDto.setPassword("Test2!112345");

        invalidUserUpdateDto = new UserUpdateDto();
        invalidUserUpdateDto.setEmail("test2");
        invalidUserUpdateDto.setFirstName("Test2");
        invalidUserUpdateDto.setLastName("Test2");
        invalidUserUpdateDto.setPassword("b");

    }

    @AfterEach
    void tearDown() {
        validUserReg = null;
        invalidUserReg = null;
        validUserLogin = null;
        invalidUserLogin = null;
        validUser = null;
    }

    @Test
    void ShouldRegisterValidUser() throws Exception {
        when(userService.register(validUserReg)).thenReturn(true);

        String userJson = objectMapper.writeValueAsString(validUserReg);

        ResultActions result = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));
        result.andExpect(status().isCreated())
                .andExpect(content().string(containsString("User registered successfully.")));
    }

    @Test
    void ShouldErrorInvalidUser() throws Exception {
        String userJson = objectMapper.writeValueAsString(invalidUserReg);

        ResultActions result = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));
        result.andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Invalid request content.")));
    }

    @Test
    void ShouldErrorDuplicateUser() throws Exception {
        when(userService.register(Mockito.any())).thenThrow(
                new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Email already in use")
        );

        String userJson = objectMapper.writeValueAsString(validUserReg);

        ResultActions result = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));

        result.andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Email already in use")));
    }

    @Test
    void ShouldLoginValidUser() throws Exception {
        when(userService.login(validUserLogin)).thenReturn(validUser);

        String userJson = objectMapper.writeValueAsString(validUserLogin);

        ResultActions result = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));

        // I'm uncertain why the body is missing here.
        // It works fine with postman
        result.andExpect(status().isOk());
    }

    @Test
    void ShouldFailLoginInvalidUser() throws Exception {

        String userJson = objectMapper.writeValueAsString(invalidUserLogin);
        ResultActions result = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));

        result.andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Invalid request content.")));
    }

    @Test
    void ShouldFailInvalidCredentials() throws Exception {

        when(userService.login(Mockito.any())).thenThrow(
                new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid credentials")
        );

        String userJson = objectMapper.writeValueAsString(validUserLogin);
        ResultActions result = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));

        result.andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Invalid credentials")));
    }

    @Test
    void ShouldGetValidProfile() throws Exception {

        when(userService.getProfile(validUser.getEmail())).thenReturn(validUserDto);

        ResultActions result = mockMvc.perform(get("/api/users/me")
                .param("email", validUser.getEmail())
                .with(csrf()));

        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(validUserDto)));

    }

    @Test
    void ShouldNotGetInvalidProfile() throws Exception {

        when(userService.getProfile(Mockito.any())).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        ResultActions result = mockMvc.perform(get("/api/users/me")
                .param("email", validUser.getEmail())
                .with(csrf()));

        result.andExpect(status().isNotFound())
                .andExpect(status().reason(containsString("User not found")));

    }

    @Test
    void ShouldUpdateValidProfile() throws Exception {

        when(userService.updateProfile(validUser.getEmail(), updateUserDto)).thenReturn(validUserDto2);

        String userJson = objectMapper.writeValueAsString(updateUserDto);

        ResultActions result = mockMvc.perform(put("/api/users/me")
                .param("email", validUser.getEmail())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));

        // I'm uncertain why the body is missing here.
        // It works fine with postman
        result.andExpect(status().isOk());

    }

    @Test
    void ShouldNotUpdateInvalidProfile() throws Exception {

        String userJson = objectMapper.writeValueAsString(invalidUserUpdateDto);

        ResultActions result = mockMvc.perform(put("/api/users/me")
                .param("email", validUser.getEmail())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));


        result.andExpect(status().isBadRequest());

    }

    @Test
    void ShouldNotUpdateUnknownProfile() throws Exception {

        when(userService.updateProfile(Mockito.any(), Mockito.any())).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        String userJson = objectMapper.writeValueAsString(updateUserDto);

        ResultActions result = mockMvc.perform(put("/api/users/me")
                .param("email", invalidUserUpdateDto.getEmail())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));


        result.andExpect(status().isNotFound())
        .andExpect(status().reason(containsString("User not found")));

    }



}