package com.sefa.challenge.bookrecommendationservice.controller;

import com.sefa.challenge.bookrecommendationservice.model.User;
import com.sefa.challenge.bookrecommendationservice.repository.UserRepository;
import com.sefa.challenge.bookrecommendationservice.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;


    @Before
    public void init() {

        User user = new User();
        user.setUserId(1);
        user.setFirstName("sefa");
        user.setLastName("oduncuoglu");
        user.setEmail("sefaoduncuoglu@gmail.com");
        user.setCreatedAt(Utils.getDateWithoutTimeUsingCalendar());
        user.setUpdatedAt(Utils.getDateWithoutTimeUsingCalendar());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    public void get_user_OK() throws Exception {

        mockMvc.perform(
                get("/api/v1/users/user").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("userId", "1"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.firstName", is("sefa")))
                .andExpect(jsonPath("$.lastName", is("oduncuoglu")))
                .andExpect(jsonPath("$.email", is("sefaoduncuoglu@gmail.com")))
                .andExpect(jsonPath("$.createdAt", is(Utils.getDateWithoutTimeUsingCalendar())))
                .andExpect(jsonPath("$.updatedAt", is(Utils.getDateWithoutTimeUsingCalendar())));


        verify(userRepository, times(1)).findById(1L);

    }
}
