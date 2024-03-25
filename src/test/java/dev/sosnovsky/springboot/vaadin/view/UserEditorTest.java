package dev.sosnovsky.springboot.vaadin.view;

import com.vaadin.flow.data.binder.Binder;
import dev.sosnovsky.springboot.vaadin.model.User;
import dev.sosnovsky.springboot.vaadin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserEditorTest {

    @Mock
    private UserService userService;
    private UserEditor userEditor;

    @BeforeEach
    void setUp() {
        userEditor = new UserEditor(userService);
//        userEditor =
//        UserEditor userEditor = ;
    }

    @Test
    void testValidateAndSave_Success() {

//        Binder<User> binder = new Binder<>(User.class);

        doCallRealMethod().when(userEditor).validateAndSave();

        when(userEditor.binder).thenReturn(mock(Binder.class));
        when(userService.getUserByEmail(anyString())).thenReturn(null);
        doNothing().when(userService).createUser(any(User.class));
        when(userService.getUserByEmail(anyString())).thenReturn(Optional.empty());
        when(userService.getUserByPhoneNumber(anyString())).thenReturn(Optional.empty());

        userEditor.validateAndSave();

        verify(userService, times(1)).createUser(any(User.class));
//        verify(changeHandler, times(1)).onChange();
    }

   /* @Test
    void testValidateAndSave_UserExists() {
        // Arrange
        when(userService.getUserByEmail(anyString())).thenReturn(null);

        // Act
        userEditor.validateAndSave();

        // Assert
        verify(userService, never()).createUser(any(User.class));
        verify(changeHandler, never()).onChange();
        // Add more assertions if needed
    }

    @Test
    void testDelete_UserExists() {
        // Arrange
        when(userService.getUserByEmail(anyString())).thenReturn(Optional.of(new User()));

        // Act
        userEditor.delete();

        // Assert
        verify(userService, times(1)).deleteUser(any(User.class));
        verify(changeHandler, times(1)).onChange();
        // Add more assertions if needed
    }

    @Test
    void testDelete_UserNotExists() {
        // Arrange
        when(userService.getUserByEmail(anyString())).thenReturn(null);

        // Act
        userEditor.delete();

        // Assert
        verify(userService, never()).deleteUser(any(User.class));
        verify(changeHandler, never()).onChange();
        // Add more assertions if needed
    }

    @Test
    void testSaveUser() {
        User user = new User();

        userEditor.validateAndSave();

        verify(userEditor, times(1)).validateAndSave();
    }*/

}
