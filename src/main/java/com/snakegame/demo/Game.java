package com.snakegame.demo;

import java.util.LinkedList;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Game {
    private LinkedList<Position> snake;
    private Position food;
    private String direction;
    private boolean gameOver;
    private final int WIDTH = 20; // Game board width in grid units
    private final int HEIGHT = 20; // Game board height in grid units
    private Random random;

    public Game() {
        this.snake = new LinkedList<>();
        this.snake.add(new Position(5, 5)); // Initial snake position
        this.food = new Position(10, 10); // Food's initial position
        this.direction = "RIGHT"; // Initial direction
        this.gameOver = false;
        this.random = new Random();
    }

    public void move() {
        if (gameOver) {
            return; // If the game is over, do nothing
        }

        // Get the current head of the snake
        Position head = snake.getFirst();
        Position newHead = new Position(head.getX(), head.getY());

        // Update the position of the head based on the current direction
        switch (direction) {
            case "UP":
                newHead.setY(head.getY() - 1);
                break;
            case "DOWN":
                newHead.setY(head.getY() + 1);
                break;
            case "LEFT":
                newHead.setX(head.getX() - 1);
                break;
            case "RIGHT":
                newHead.setX(head.getX() + 1);
                break;
        }

        // Check if the snake has eaten food
        if (newHead.getX() == food.getX() && newHead.getY() == food.getY()) {
            // Grow the snake by adding the new head (no need to remove the last tail
            // element)
            snake.addFirst(newHead);

            // Generate new food
            generateFood();
        } else {
            // Move the snake: add the new head and remove the last tail element
            snake.addFirst(newHead);
            snake.removeLast();
        }

        // Check for wall collisions
        if (newHead.getX() < 0 || newHead.getX() >= WIDTH || newHead.getY() < 0 || newHead.getY() >= HEIGHT) {
            gameOver = true;
            return; // If the snake hits the wall, the game is over
        }

        // Check for collisions with itself (if the head collides with any part of the
        // body)
        for (int i = 1; i < snake.size(); i++) {
            Position bodyPart = snake.get(i);
            if (newHead.getX() == bodyPart.getX() && newHead.getY() == bodyPart.getY()) {
                gameOver = true;
                return; // If the snake runs into itself, the game is over
            }
        }
    }

    // Method to generate new food in a random position
    private void generateFood() {
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);

        // Ensure food doesn't spawn on the snake
        while (snake.contains(new Position(x, y))) {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
        }

        food.setX(x);
        food.setY(y);
    }

    // Getters and setters for snake, food, direction, and game state
    public LinkedList<Position> getSnake() {
        return snake;
    }

    public Position getFood() {
        return food;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void reset() {
        this.snake.clear();
        this.snake.add(new Position(5, 5)); // Reset the snake to initial position
        this.food = new Position(10, 10); // Reset the food to a new position
        this.direction = "RIGHT"; // Reset the direction to initial state
        this.gameOver = false;
    }
}
