package ro.utcluj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.utcluj.model.Client;
import ro.utcluj.model.CustomUserDetails;
import ro.utcluj.repositories.ClientRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(s);

        if(client == null) throw new UsernameNotFoundException ("Couldn't find account!");

        return new CustomUserDetails (client);


    }


}
