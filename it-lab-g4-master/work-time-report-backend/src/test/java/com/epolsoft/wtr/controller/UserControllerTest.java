package com.epolsoft.wtr.controller;


import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAddEmployee()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User user = new User();
        user.setUserId(1);

        when(userService.createOrUpdate(any(User.class))).thenReturn(user);

        User userToAdd = new User("Lokesh", "pass1");
        ResponseEntity<Object> responseEntity = userController.addUser(userToAdd);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");

    }



    @Test
    public void testFindAll()
    {
        // given
        User user1 = new User("Lusia", "pass1");
        User user2 = new User("Atvi", "pass2");

        List<User> list = new ArrayList<User>();
        list.addAll(Arrays.asList(user1, user2));

        when(userService.findAll()).thenReturn(list);

        // when
        List<User> result = userController.getAllUser();

        // then
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getUserName())
                .isEqualTo(user1.getUserName());

        assertThat(result.get(1).getUserName())
                .isEqualTo(user2.getUserName());

    }

}
