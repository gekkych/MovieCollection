package ru.se.ifmo.s466351.lab6.client.handler;


import ru.se.ifmo.s466351.lab6.common.dto.*;
import ru.se.ifmo.s466351.lab6.common.exception.MovieFieldNotValidatedException;
import ru.se.ifmo.s466351.lab6.common.util.MovieValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MovieFieldInput {
    private static final Scanner scanner = new Scanner(System.in);
    static final String GECKO = "\uD83E\uDD8E";

    public static String inputTitle() {
        while (true) {
            System.out.println("Введите название фильма (строка, не должно быть null или пустой строкой):");
            System.out.print(GECKO + " > ");
            String title = scanner.nextLine().trim();
            try {
                MovieValidator.validateTitle(title);
                return title;
            } catch (MovieFieldNotValidatedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int inputX() {
        while (true) {
            System.out.println("Введите координату Х (число):");
            System.out.print(GECKO + " > ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Поле x должно быть числом.");
            }
        }
    }

    public static Double inputY() {
        while (true) {
            System.out.println("Введите координату Y (число, должно быть меньше 102):");
            System.out.print(GECKO + " > ");
            try {
                Double y = Double.parseDouble(scanner.nextLine().trim());
                try {
                    MovieValidator.validateY(y);
                    return y;

                } catch (MovieFieldNotValidatedException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("Значение y должно быть числом.");
            }
        }
    }

    public static int inputOscarCount() {
        while (true) {
            System.out.println("Введите количество оскаров (число, больше 0):");
            System.out.print(GECKO + " > ");
            try {
                int oscarCount = Integer.parseInt(scanner.nextLine().trim());
                try {
                    MovieValidator.validateOscarCount(oscarCount);
                    return oscarCount;
                } catch (MovieFieldNotValidatedException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("Значение oscarCount должно быть числом.");
            }
        }
    }

    public static MovieGenre inputGenre() {
        while (true) {
            System.out.println("Введите жанр фильма:");
            System.out.println("Action, Comedy, Science fiction");
            System.out.print(GECKO + " > ");
            MovieGenre genre = switch (scanner.nextLine().trim().toLowerCase()) {
                case "action" -> MovieGenre.ACTION;
                case "comedy" -> MovieGenre.COMEDY;
                case "science fiction" -> MovieGenre.SCIENCE_FICTION;
                default -> null;
            };
            try {
                MovieValidator.validateGenre(genre);
                return genre;
            } catch (MovieFieldNotValidatedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static MpaaRating inputRating() {
        while (true) {
            System.out.println("Введите возрастной рейтинг фильма (нажмите Enter если хотите оставить поле пустым):");
            System.out.println("G, PG, PG 13, NC 17");
            System.out.print(GECKO + " > ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) {
                return null;
            }
            MpaaRating mpaaRating = switch (input) {
                case "g" -> MpaaRating.G;
                case "pg" -> MpaaRating.PG;
                case "pg 13" -> MpaaRating.PG_13;
                case "nc 17" -> MpaaRating.NC_17;
                default -> null;
            };
            if (mpaaRating == null) {
                System.out.println("Неверный ввод, попробуйте ещё раз");
                continue;
            }
            return mpaaRating;
        }
    }

    public static PersonDTO inputDirector() {
        final String[] CONFIRMATION_WORDS = {"y", "yes"};
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String directorName;
        LocalDate birthday = null;
        int height;
        int weight;
        boolean confirmed = false;
        System.out.println("Вы хотите ввести информацию о режиссёре? [y/n]:");
        System.out.print(GECKO + " > ");
        String input = scanner.nextLine().trim();
        for (String word : CONFIRMATION_WORDS) {
            if (input.equalsIgnoreCase(word)) {
                confirmed = true;
                break;
            }
        }
        if (!confirmed) {
            return null;
        }

        while (true) {
            System.out.println("Введите имя режиссёра (строка, не должно быть null или пустой строкой):");
            System.out.print(GECKO + " > ");
            directorName = scanner.nextLine().trim();
            try {
                MovieValidator.validateDirectorName(directorName);
                break;
            } catch (MovieFieldNotValidatedException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean birthdayIsValid = false;
        while (true) {
            System.out.println("Введите дату рождения режиссёра в формате \"dd-MM-yyyy:\" (нажмите Enter если хотите оставить поле пустым):");
            System.out.print(GECKO + " > ");
            String birthdayInput = scanner.nextLine().trim();
            if (birthdayInput.isEmpty()) {
                break;
            }
            try {
                birthday = LocalDate.parse(birthdayInput, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Неверный ввод, попробуйте ещё раз");
            }
        }

        while (true) {
            System.out.println("Введите рост режиссёра (число, больше 0):");
            System.out.print(GECKO + " > ");
            try {
                height = Integer.parseInt(scanner.nextLine().trim());
                try {
                    MovieValidator.validateDirectorHeight(height);
                    break;
                } catch (MovieFieldNotValidatedException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("height должно быть числом.");
            }
        }

        while (true) {
            System.out.println("Введите вес режиссёра:");
            System.out.print(GECKO + " > ");
            try {
                weight = Integer.parseInt(scanner.nextLine().trim());
                try {
                    MovieValidator.validateDirectorWeight(weight);
                    break;
                } catch (MovieFieldNotValidatedException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("weight должно быть числом");
            }
        }
        return new PersonDTO(directorName, birthday, height, weight);
    }

    public static CoordinateDTO inputCoordinate() {
        return new CoordinateDTO(inputX(), inputY());
    }

    public static MovieDTO inputMovieData() {
        return new MovieDTO(inputTitle(), inputCoordinate(), inputGenre(), inputRating(), inputOscarCount(), inputDirector());
    }
}
