package hse.group1.focusflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private UserRegistrationDto validUser;


    @BeforeEach
    void setUp() {
        validUser = new UserRegistrationDto();
        validUser.setEmail("test@test.com");
        validUser.setFirstName("Test");
        validUser.setLastName("Test");
        validUser.setPassword("Test!112345");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void ShouldRegisterValidUser() throws Exception {
        when(userService.register(validUser)).thenReturn(true);

        String userJson = objectMapper.writeValueAsString(validUser);

        ResultActions result = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()));
        result.andExpect(status().isCreated())
                .andExpect(content().string(containsString("User registered successfully.")));
    }


}