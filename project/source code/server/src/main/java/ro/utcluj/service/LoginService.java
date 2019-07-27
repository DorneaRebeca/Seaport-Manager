package ro.utcluj.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.utcluj.api.ClientBaseDTO;
import ro.utcluj.api.LoginServiceInterface;
import ro.utcluj.mapper.ClientMapper;
import ro.utcluj.model.Client;
import ro.utcluj.repositories.ClientRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LoginService implements LoginServiceInterface {


    private ClientMapper clientMapper;
    private ClientRepository repository;
    private ContextGetter contextGetter;

    public LoginService (ContextGetter contextGetter, ClientMapper clientMapper, ClientRepository clientRepository){
        this.clientMapper = clientMapper;
        this.repository = clientRepository;
        this.contextGetter = contextGetter;
    }



    public ClientBaseDTO addClient(String name, String username, int currency, String password, String shipType, String email) throws Exception {
        Client client = null;
        List<Client> clients = repository.findAll ();
        for(Client c : clients){
            if(c.getUsername ().equals (username))
                throw new Exception ("Username already used!!!! Change it");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();
        String encoded = passwordEncoder.encode(password);
        if(!email.contains ("@"))
            throw new Exception ("Incorrect email!!!");
        else {
            if(currency > 1000000)
                 client = new Client  (name, username, encoded, shipType,"client", email, currency , true);

            else client = new Client  (name, username, encoded, shipType,"client", email, currency , false);
        }
        repository.save (client);
        return clientMapper.map (client);
    }

    public ClientBaseDTO login() throws Exception {

        Client c = repository.findByUsername (contextGetter.getUserNameFromContext ());
        return clientMapper.map (c);

    }




}
