package ro.utcluj;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.utcluj.api.ClientBaseDTO;
import ro.utcluj.mapper.ClientMapper;
import ro.utcluj.model.Client;
import ro.utcluj.repositories.ClientRepository;
import ro.utcluj.service.ContextGetter;
import ro.utcluj.service.LoginService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginServiceTest {


    @Mock
    ClientMapper clientMapper;

    @Mock
    ClientRepository repository;

    @Mock
    ContextGetter contextGetter;

    LoginService loginService;
    private Client client;
    private List<Client> c;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule ();

    @Before
    public void start() {
        loginService = new LoginService (contextGetter, clientMapper, repository);
        client = new Client ();
        client.setUsername ("ma");
        client.setPassword ("pass");
        client.setClienttype ("client");
        c = new LinkedList<> ();
        c.add (client);
    }


    @Test
    public void testLogin() throws Exception {


        when (repository.findByUsername (anyString ())).thenReturn (client);
        when (clientMapper.map (anyObject ())).thenReturn (new ClientBaseDTO ());
        loginService.login ();
        verify (repository, times (1)).findByUsername (anyString ());
        verify (clientMapper, times (1)).map (anyObject ());
    }

    @Test
    public void addClientTest() throws Exception {
        when (repository.findAll ()).thenReturn (c);
        when (repository.save (anyObject ())).thenReturn (new Client ());
        when (clientMapper.map (anyObject ())).thenReturn (new ClientBaseDTO ());
        loginService.addClient (anyString (), eq ("ma"), anyInt (), anyString (), anyString (), anyString ());
        verify (repository, times (1)).findAll ();
        verify (repository, times (1)).save (anyObject ());
        verify (clientMapper, times (1)).map (anyObject ());
    }
}
