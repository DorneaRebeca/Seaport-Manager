package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.ClientBaseDTO;
import ro.utcluj.model.Client;

import java.util.LinkedList;
import java.util.List;

@Component
public class ClientMapper {

    public ClientBaseDTO map (Client client){
        ClientBaseDTO cl = new ClientBaseDTO ();
        cl.setPassword (client.getPassword ());
        cl.setShiptype (client.getShiptype ());
        cl.setName (client.getName ());
        cl.setUsername (client.getUsername ());
        cl.setIdclient (client.getIdclient ());
        cl.setClienttype (client.getClienttype ());
        cl.setCurrency (client.getCurrency ());
        cl.setEmail (client.getEmail ());
        return cl;
    }

    public List<ClientBaseDTO> mapAll(List<Client> clients){
        List<ClientBaseDTO> cls = new LinkedList<> ();
        for(Client c : clients)
            cls.add (map (c));
        return cls;
    }


    public Client mapBackward (ClientBaseDTO client){
        Client cl = new Client ();
        cl.setPassword (client.getPassword ());
        cl.setShiptype (client.getShiptype ());
        cl.setName (client.getName ());
        cl.setUsername (client.getUsername ());
        cl.setIdclient (client.getIdclient ());
        cl.setClienttype (client.getClienttype ());
        cl.setCurrency (client.getCurrency ());
        cl.setEmail (client.getEmail ());
        return cl;
    }


}
