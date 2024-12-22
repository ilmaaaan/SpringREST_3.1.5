package com.spring.rest;

import com.spring.rest.configuration.MyConfig;
import com.spring.rest.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = applicationContext.getBean("communication" ,Communication.class);


        List<User> users = communication.getAllUsers();
        System.out.println(users);

        //Сохранение нового пользователя
        User user = new User(3L, "James", "Brown", (byte)20);
        communication.saveUser(user);

        //Изменение нового пользователя
        User editUser = new User(3L, "Thomas", "Shelby", (byte)20);
        communication.updateUser(editUser);

        //Удаление пользователя
        communication.deleteUser(3L);
    }
}