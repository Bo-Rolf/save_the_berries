package com.mycompany.app;

import com.mycompany.app.model.Model;

// Bootstrap of the project, contains the main method

import com.mycompany.app.view.View;

import com.mycompany.app.controller.Controller;

public class App {

    public static void main(String[] args) {
        App app = new App();

    }

    public App() {
        
        // Create the MVC Model
        // Declare the three parts of the MVC paradigm
        Model model = new Model();
        
        // Create the MVC View
        View view = new View(model);
        // Create the MVC Controller
        //Controller controller = new Controller(model);

    }
}
