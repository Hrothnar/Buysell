package com.neo.buysell.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.buysell.model.dto.PasswordUpdaterDTO;
import com.neo.buysell.model.dto.UserUpdaterDTO;
import com.neo.buysell.model.entity.Role;
import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.service.RoleService;
import com.neo.buysell.model.service.UserService;
import com.neo.buysell.model.util.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "user@mail.ru", password = "password", setupBefore = TestExecutionEvent.TEST_EXECUTION)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mokMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void setPassword_shouldSetPassword() throws Exception {
        String password = userService.getUser("user@mail.ru").getPassword();
        PasswordUpdaterDTO passwordUpdaterDTO = new PasswordUpdaterDTO();
        passwordUpdaterDTO.newPassword = "00000000"; //8
        passwordUpdaterDTO.currentPassword = "11111111"; //8
        String json = objectMapper.writeValueAsString(passwordUpdaterDTO);

        mokMvc.perform(MockMvcRequestBuilders.post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getBytes()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        User user = userService.getUser("user@mail.ru");
        Assertions.assertNotEquals(password, user.getPassword());
    }

    @Test
    public void getUser_shouldGetUser() throws Exception {

        mokMvc.perform(MockMvcRequestBuilders.get("/users/me"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Todd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(Paths.GET_AVATAR_ENDPOINT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+71234567890"));
    }

    @Test
    public void updateInfo_shouldUpdateInfo() throws Exception {
        UserUpdaterDTO userUpdaterDTO = new UserUpdaterDTO();
        userUpdaterDTO.firstName = "John";
        userUpdaterDTO.lastName = "Vasilkov";
        userUpdaterDTO.phone = "+71234567890";
        String json = objectMapper.writeValueAsString(userUpdaterDTO);
        User user = userService.getUser("user@mail.ru");
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phone = user.getPhone();

        mokMvc.perform(MockMvcRequestBuilders.patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getBytes()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"));

        User updatedUser = userService.getUser("user@mail.ru");
        Assertions.assertNotEquals(firstName, updatedUser.getFirstName());
        Assertions.assertNotEquals(lastName, updatedUser.getLastName());
        Assertions.assertEquals(phone, updatedUser.getPhone());
    }

    @Test
    public void updateAvatar_shouldUpdateAvatar() throws Exception {
        byte[] bytes = Files.readAllBytes(Path.of(Paths.STANDARD_AD_IMAGE_PATH));
        File file = new File(Paths.STANDARD_AD_IMAGE_PATH);
        String name = file.getName();

        String avatarPath = userService.getUser("user@mail.ru").getAvatarPath();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", name, MediaType.IMAGE_JPEG_VALUE, bytes);

        mokMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, "/users/me/image")
                        .file(mockMultipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        User user = userService.getUser("user@mail.ru");
        Assertions.assertNotEquals(avatarPath, user.getAvatarPath());
        Assertions.assertTrue(user.getAvatarPath().contains(name));

        Files.deleteIfExists(Path.of(user.getAvatarPath()));
    }

    @Test
    public void getAvatar_shouldGetAvatar() throws Exception {
        String avatarPath = userService.getUser("user@mail.ru").getAvatarPath();
        byte[] bytes = Files.readAllBytes(Path.of(avatarPath));

        mokMvc.perform(MockMvcRequestBuilders.get("/users/me/image"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(bytes))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.IMAGE_JPEG));
    }


    @BeforeEach
    public void fillDataBase() {
        User user = createUser("user@mail.ru", "+71234567890");
        userService.saveUser(user);
    }

    private User createUser(String username, String phone) {
        Role userRole = roleService.getRole("ROLE_USER");
        User user = new User();
        user.setEmail(username);
        user.setFirstName("Todd");
        user.setLastName("Sweeney");
        user.setPhone(phone);
        user.setPassword("password");
        user.setUsername(username);
        user.setAvatarPath(Paths.STANDARD_AVATAR_PATH);
        user.getRoles().add(userRole);
        return user;
    }

}
