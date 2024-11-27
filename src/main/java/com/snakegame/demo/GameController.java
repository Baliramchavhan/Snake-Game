

package com.snakegame.demo;


// 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


// @Controller
// public class GameController {

//     // Store the game instance as a session attribute to maintain game state across
//     // requests
//     @Autowired
//     private Game game;

//     @GetMapping("/")
//     public String game(Model model) {
//         model.addAttribute("game", new Game()); // Send the game state to the frontend
//         return "game"; // The HTML page will use this data to render the game state
//     }

//     // Handle the snake's direction change
//     @PostMapping("/move")
//     public String move(@RequestParam String direction, Model model) {
//         // Update the snake's direction
//         game.setDirection(direction);

//         // Move the snake
//         game.move();

//         // Send updated game state to the frontend
//         model.addAttribute("game", game);

//         // Return the game page again with the updated state
//         return "game";
//     }

//     // Handle resetting the game
//     @PostMapping("/reset")
//     public String reset(Model model) {
//         game.reset(); // Reset the game to its initial state
//         model.addAttribute("game", game);
//         return "game";
//     }
// }
@Controller
public class GameController {

    @Autowired
    private Game game;

    @GetMapping("/")
    public String game(Model model) {
        model.addAttribute("game", new Game()); // Add the game state to the model
        return "game"; // Return the Thymeleaf template "game.html"
    }

    // Handle the snake's direction change
    @PostMapping("/move")
    @ResponseBody
    public Game move(@RequestParam String direction) {
        game.setDirection(direction);
        game.move();
        return game; // Return the updated game state as JSON
    }

    // Handle resetting the game
    @PostMapping("/reset")
    @ResponseBody
    public Game reset() {
        game.reset();
        return game; // Return the reset game state as JSON
    }
}
