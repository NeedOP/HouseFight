package se.eli.adventure.Game;

import se.eli.adventure.Model.Burglar;
import se.eli.adventure.Model.Entity;
import se.eli.adventure.Model.Resident;

import java.util.Scanner;

public class Game {
    private Scanner scanner = new Scanner(System.in);
    private boolean Running = true;
    private Resident resident;
    private Burglar burglar;
    private RoomType CurrentLocation = RoomType.LIVING_ROOM;
    private boolean HasPan = false;

    public String getUserInout() {
        return scanner.nextLine();
    }

    public static void Return() {
        System.out.println("1.) Return to the living room");
        System.out.println("2.) Exit the game");
    }

    public Game(Resident resident, Burglar burglar) {
        this.resident = resident;
        this.burglar = burglar;
    }

    public boolean IsRunning() {
        return Running;
    }

    public void fight(Entity attacker, Entity defender) {
        while (IsRunning() && attacker.isConsious() && defender.isConsious()) {
            // Player attacks the enemy
            System.out.println("Press Enter to Strike!");
            getUserInout();
            attacker.attack(defender);
            System.out.println("You hit and deal " + attacker.getDamage() + " damage. " +
                    defender.getRole() + " has " + defender.getHealth() + " HP left.");

            // Check if the burglar is still conscious
            if (!defender.isConsious()) {
                System.out.println(defender.getRole() + " is knocked!");
                System.out.println("Hurry Go to the office and call the police");
                Return();
                break;
            }

            // Enemy attacks the player
            defender.attack(attacker);
            System.out.println(defender.getRole() + " hits you and deals " + defender.getDamage() +
                    " damage. You have " + attacker.getHealth() + " HP left.");
            System.out.println();

            // Check if the player is still conscious
            if (!attacker.isConsious()) {
                System.out.println("You are dead. Game over.");
                Running = false; // End the game if the player is dead
                break;
            }


        }
    }

    public void livingRoom() {
        CurrentLocation = RoomType.LIVING_ROOM;
        System.out.println("You are in the living room");
        System.out.println("Chose where to go:");
        System.out.println("1.) Bedroom");
        System.out.println("2.) Kitchen");
        System.out.println("3.) Office");
        System.out.println("4) Hallway");
        System.out.println("5) Exit Game");
    }

    public void bedroom() {
        System.out.println("------------------------------------------------");
        System.out.println("Nothing to see, only some clothe on the floor");
        Return();
    }

    public void handleKitchen() {
        System.out.println("------------------------------------------------");
        System.out.println("Nothing suspicious over here");
        System.out.println("You see a frying pan");
        System.out.println("Do you chose to take it?");
        System.out.println("1.) yes");
        System.out.println("2.) no");
        String input = getUserInout();
        switch (input) {
            case "1" -> {
                resident.addDamage(3);
                System.out.println("------------------------------------------------");
                System.out.println("You take the frying pan");
                System.out.println("It increases your damage by 3");
                HasPan = true;

            }
            case "2" -> {
                System.out.println("------------------------------------------------");
                System.out.println("You skip the frying pan");
            }
            default -> {
                System.out.println("Invalid input");
            }
        }
        Return();
    }

    public void kitchen() {
        System.out.println("---------------------------------------------");
        System.out.println("Nothing suspicious over here");
        Return();
    }

    public void office() {
        System.out.println("---------------------------------------------");
        System.out.println("None is here, its just your office");
        Return();
    }

    public void handleOffice() {
        System.out.println("---------------------------------------------");
        System.out.println("You see the phone do you call the police?");
        System.out.println("1.) yes");
        System.out.println("2.) no");
        String input = getUserInout();
        switch (input) {
            case "1" -> {
                System.out.println("---------------------------------------------");
                System.out.println("You call the police");
                System.out.println("They come immediately");
                System.out.println("You win the Game");
                Running = false;
            }
            case "2" -> {
                System.out.println("------------------------------------------------");
                System.out.println("You skip calling the police");
                Return();
            }
            default -> {
                System.out.println("Invalid input");
            }
        }

    }

    public void handleHallway() {
        System.out.println("-------------------------------------------");
        System.out.println("You see a burglar");
        System.out.println("You shout at him ");
        System.out.println("He shouts at you back and wants to fight");
        fight(resident, burglar);
    }

    public void hallway() {
        System.out.println("--------------------------------------------");
        System.out.println("You enter and see the burglar is still unconscious");
        Return();
    }

    public boolean ProcessInput(String input) {
        if (CurrentLocation == RoomType.LIVING_ROOM) {
            switch (input) {
                case "1" -> {
                    CurrentLocation = RoomType.BEDROOM;
                    bedroom();
                }
                case "2" -> {
                    CurrentLocation = RoomType.KITCHEN;
                    if (HasPan == false) {
                        handleKitchen();
                    } else {
                        kitchen();
                    }
                }
                case "3" -> {
                    CurrentLocation = RoomType.OFFICE;
                    if (!burglar.isConsious()) {
                        handleOffice();
                    } else {
                        office();
                    }
                }
                case "4" -> {
                    CurrentLocation = RoomType.HALLWAY;
                    if (!burglar.isConsious()) {
                        hallway();
                    } else {
                        handleHallway();
                    }
                }
                case "5" -> {
                    System.out.println("You chose to exit game");
                    System.out.println("Game over");
                    Running = false;
                }
                default -> {
                    System.out.println("Invalid input");
                }
            }
        } else {
            switch (input) {
                case "1" -> {
                    CurrentLocation = RoomType.LIVING_ROOM;
                    livingRoom();
                }
                case "2" -> {
                    System.out.println("You chose to exit game");
                    Running = false;
                }
                default -> {
                    System.out.println("Invalid input");
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Resident resident = new Resident("Resident", 12, 3);
        Burglar burglar = new Burglar("Burglar", 12, 4);
        Game game = new Game(resident, burglar);


        System.out.println("------------------------------------------------");
        System.out.println("BOOM");
        System.out.println("You hear a sound and wake up");
        System.out.println("You stand up to check it out");
        game.livingRoom();
        while (game.IsRunning()) {
            String input = game.getUserInout();
            game.ProcessInput(input);
        }
    }
}
