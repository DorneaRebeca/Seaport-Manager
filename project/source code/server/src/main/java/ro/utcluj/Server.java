package ro.utcluj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.utcluj.api.AdminServiceInterface;
import ro.utcluj.api.ClientServiceInterface;
import ro.utcluj.api.LoginServiceInterface;
import ro.utcluj.api.StringProcesorInterface;
import ro.utcluj.service.AdminService;
import ro.utcluj.service.ClientService;
import ro.utcluj.service.LoginService;
import ro.utcluj.service.StringProcessor;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class Server {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Server.class, args);

        System.setProperty("java.awt.headless", "false");
    }


    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean(name = "/client")
    public HttpInvokerServiceExporter studentServiceExporter(ClientService service){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(ClientServiceInterface.class);
        return exporter;
    }


    @Bean(name = "/admin")
    public HttpInvokerServiceExporter adminServiceExporter(AdminService service){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(AdminServiceInterface.class);
        return exporter;
    }


    @Bean(name = "/stringProcessor")
    public HttpInvokerServiceExporter strProcServiceExporter(StringProcessor service){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(StringProcesorInterface.class);
        return exporter;
    }

    @Bean(name = "/login")
    public HttpInvokerServiceExporter loginServiceExporter(LoginService service){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(LoginServiceInterface.class);
        return exporter;
    }





}
