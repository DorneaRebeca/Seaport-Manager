package ro.utcluj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.security.remoting.httpinvoker.AuthenticationSimpleHttpInvokerRequestExecutor;
import ro.utcluj.api.*;


@SpringBootApplication
public class ClientApp extends Application {

    private FXMLLoader fxmlLoader;


    public static void main(String[] args) {
        //System.setProperty("java.awt.headless", "false");
        launch(args);
    }



    @Override
    public void init() throws Exception{
        ApplicationContext context = SpringApplication.run(ClientApp.class);
      // ClientServiceInterface srv = context.getBean (ClientServiceInterface.class);

        //srv.registerClient ("HelloWorld","hello",1223753,"pass" , "yacht" , "he@gmail.com");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);


    }

    @Override
    public void start(Stage stage) throws Exception {
        fxmlLoader.setLocation(getClass().getResource("/LoginPage.fxml"));
        fxmlLoader.setClassLoader(this.getClass().getClassLoader());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }


    @Bean
    public AuthenticationSimpleHttpInvokerRequestExecutor httpInvokerRequestExecutor() { // spring-security-remoting dependency (check latest version here https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-dependency-versions.html)
        return new AuthenticationSimpleHttpInvokerRequestExecutor();
    }



    @Bean
    public HttpInvokerProxyFactoryBean studentServiceProxy(AuthenticationSimpleHttpInvokerRequestExecutor requestExecutor){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setHttpInvokerRequestExecutor(requestExecutor);
        proxy.setServiceInterface(ClientServiceInterface.class);
        proxy.setServiceUrl("http://localhost:1234/client");
        return proxy;
    }


    @Bean
    public HttpInvokerProxyFactoryBean loginServiceProxy(AuthenticationSimpleHttpInvokerRequestExecutor requestExecutor){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setHttpInvokerRequestExecutor(requestExecutor);
        proxy.setServiceInterface(LoginServiceInterface.class);
        proxy.setServiceUrl("http://localhost:1234/login");
        return proxy;
    }

    @Bean
    public HttpInvokerProxyFactoryBean adminServiceProxy(AuthenticationSimpleHttpInvokerRequestExecutor requestExecutor){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setHttpInvokerRequestExecutor(requestExecutor);
        proxy.setServiceInterface(AdminServiceInterface.class);
        proxy.setServiceUrl("http://localhost:1234/admin");
        return proxy;
    }
    @Bean
    public HttpInvokerProxyFactoryBean strProcessorServiceProxy(AuthenticationSimpleHttpInvokerRequestExecutor requestExecutor){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setHttpInvokerRequestExecutor(requestExecutor);
        proxy.setServiceInterface(StringProcesorInterface.class);
        proxy.setServiceUrl("http://localhost:1234/stringProcessor");
        return proxy;
    }


}
