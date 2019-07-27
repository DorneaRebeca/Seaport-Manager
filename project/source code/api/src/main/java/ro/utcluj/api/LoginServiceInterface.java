package ro.utcluj.api;

public interface LoginServiceInterface {

    ClientBaseDTO login() throws  Exception;
    ClientBaseDTO addClient(String name,String usern, int currency, String pass, String shipType, String email ) throws Exception;




}
