package org.example;

import contracts.CatsOperationResult;
import contracts.ICatsService;
import contracts.IOwnerService;
import contracts.OwnerOperationResult;
import dto.CatDTO;
import models.Cat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@ComponentScan(basePackages = {"buisinessLogic", "configs", "restControllers", "repositories"})
//@EnableJpaRepositories(basePackages = {"dao"})
@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class);
        /*ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        IOwnerService ownerService = context.getBean("ownerServiceBean", IOwnerService.class);

        if (ownerService.getAllOwnerCats(1L) instanceof OwnerOperationResult.SuccessCheckingCats cats){
                for (var cat: cats.cats()) {
                    System.out.println(cat.getName());
                    System.out.println(cat.getOwnerId());
                }
        }



        System.out.println(8);*/

    }
}