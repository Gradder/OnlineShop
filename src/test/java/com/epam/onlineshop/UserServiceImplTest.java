package com.epam.onlineshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.onlineshop.entities.Role;
import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.repository.UserRepository;
import com.epam.onlineshop.services.impl.UserServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Mock
  private User user;

  private UserServiceImpl service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    service = Mockito.spy(new UserServiceImpl(userRepository, bCryptPasswordEncoder));
  }

  @Test
  public void addUser() {
    String username = "username";
    User expected = User.builder()
        .role(Role.USER)
        .username(username)
        .firstName(user.getFirstName())
        .isBlocked(false)
        .password(bCryptPasswordEncoder.encode(user.getPassword()))
        .address(user.getAddress())
        .secondName(user.getSecondName())
        .phoneNumber(user.getPhoneNumber())
        .build();
    when(userRepository.findByUsername(username)).thenReturn(null);

    boolean actual = service.addUser(expected);

    assertTrue("Returned object doesn't match", actual);
    verify(userRepository).save(expected);
    verify(userRepository).findByUsername(username);
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  public void addUser_negative() {
    String username = "username";
    when(user.getUsername()).thenReturn(username);
    when(userRepository.findByUsername(username)).thenReturn(user);

    boolean actual = service.addUser(user);

    assertFalse("Returned object doesn't match", actual);
    verify(userRepository).findByUsername(username);
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  public void findByUsername() {
    String username = "username";
    User expected = User.builder()
        .role(Role.USER)
        .username(username)
        .firstName(user.getFirstName())
        .isBlocked(false)
        .password(bCryptPasswordEncoder.encode(user.getPassword()))
        .address(user.getAddress())
        .secondName(user.getSecondName())
        .phoneNumber(user.getPhoneNumber())
        .build();
    when(userRepository.findByUsername(username)).thenReturn(expected);

    User actual = service.findByUsername(username);

    assertEquals("Returned object doesn't match", expected, actual);
    verify(userRepository).findByUsername(username);
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  public void updateUser() {
    String username = "username";
    String address = "address";
    String firstName = "firstName";
    String secondName = "secondName";
    String phoneNumber = "phoneNumber";
    when(user.getUsername()).thenReturn(username);
    when(user.getAddress()).thenReturn(address);
    when(user.getFirstName()).thenReturn(firstName);
    when(user.getSecondName()).thenReturn(secondName);
    when(user.getPhoneNumber()).thenReturn(phoneNumber);
    when(userRepository.findByUsername(username)).thenReturn(user);

    service.updateUser(user);

    verify(userRepository).findByUsername(username);
    verify(userRepository).save(user);
    verifyNoMoreInteractions(userRepository);
    verify(user).setAddress(address);
    verify(user).setPhoneNumber(phoneNumber);
    verify(user).setFirstName(firstName);
    verify(user).setSecondName(secondName);
  }

  @Test
  public void getAllUsers() {

    List<User> expected = Collections.singletonList(user);
    when(userRepository.findAll()).thenReturn(expected);

    List<User> actual = service.getAllUsers();

    assertEquals("", expected, actual);

    verify(userRepository).findAll();
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  public void findById() {

    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    User actual = service.findById(userId);

    assertEquals("", user, actual);

    verify(userRepository).findById(userId);
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  public void findById_negative() {

    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    User actual = service.findById(userId);

    assertNull("", actual);

    verify(userRepository).findById(userId);
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  public void changeBlockedStatus() {
    when(user.getIsBlocked()).thenReturn(Boolean.TRUE);

    service.changeBlockedStatus(user);

    verify(userRepository).save(user);
    verifyNoMoreInteractions(userRepository);
    verify(user).setIsBlocked(Boolean.FALSE);
    verify(user).getIsBlocked();
    verifyNoMoreInteractions(user);
  }

  @Test
  public void changeBlockedStatus_secondCase() {
    when(user.getIsBlocked()).thenReturn(Boolean.FALSE);

    service.changeBlockedStatus(user);

    verify(userRepository).save(user);
    verifyNoMoreInteractions(userRepository);
    verify(user).setIsBlocked(Boolean.TRUE);
    verify(user).getIsBlocked();
    verifyNoMoreInteractions(user);
  }
}
