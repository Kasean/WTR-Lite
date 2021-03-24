package com.epolsoft.wtr.service;

import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    private static User p1;

    @Mock
    private UserRepository userCRUD;

    @InjectMocks
    private UserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new User();

    }
    
    @Test
    public void findById() {
        /*userCRUD.deleteById(1);
        Mockito.verify(userCRUD, Mockito.times(1)).deleteById(1);*/
    	Mockito.when(userCRUD.findByUserId(1)).thenReturn(p1);
        assertThat(userService.findById(1), is(p1));
        Mockito.verify(userCRUD, Mockito.times(1)).findByUserId(1);
    }

    @Test
    public void findByName() {
    	Mockito.when(userCRUD.findByName("User1")).thenReturn(p1);
        assertThat(userService.findByName("User1"), is(p1));
        Mockito.verify(userCRUD, Mockito.times(1)).findByName("User1");
    }

}
