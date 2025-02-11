import java.util.List;
import java.util.Scanner;

public class BirthdayApp {
    private static final DatabaseManager dbManager = new DatabaseManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Поздравлятор!");

        // Показываем ближайшие ДР (например, за 7 дней)
        showUpcomingBirthdays(7);

        // Запускаем меню
        showMenu();
    }

    private static void showUpcomingBirthdays(int daysAhead) {
        List<Birthday> upcoming = dbManager.getUpcomingBirthdays(daysAhead);
        System.out.println("\nСегодняшние и ближайшие дни рождения:");

        if (upcoming.isEmpty()) {
            System.out.println("Нет предстоящих дней рождений.");
        } else {
            for (Birthday b : upcoming) {
                System.out.println(b.getId() + ". " + b.getName() + " - " + b.getDate() + " (" + b.getNote() + ")");
            }
        }
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Показать все дни рождения");
            System.out.println("2. Добавить день рождения");
            System.out.println("3. Удалить день рождения");
            System.out.println("4. Редактировать день рождения");
            System.out.println("5. Выйти");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    showAllBirthdays();
                    break;
                case "2":
                    addBirthday();
                    break;
                case "3":
                    deleteBirthday();
                    break;
                case "4":
                    updateBirthday();
                    break;
                case "5":
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Некорректный ввод. Попробуйте снова.");
            }
        }
    }

    private static void showAllBirthdays() {
        List<Birthday> birthdays = dbManager.getAllBirthdays();
        if (birthdays.isEmpty()) {
            System.out.println("Список дней рождения пуст.");
        } else {
            for (Birthday b : birthdays) {
                System.out.println(b.getId() + ". " + b.getName() + " - " + b.getDate() + " (" + b.getNote() + ")");
            }
        }
    }

    private static void addBirthday() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите дату (ГГГГ-ММ-ДД): ");
        String date = scanner.nextLine();
        System.out.print("Введите заметку: ");
        String note = scanner.nextLine();

        dbManager.addBirthday(name, date, note);
        System.out.println("День рождения добавлен!");
    }

    private static void deleteBirthday() {
        System.out.print("Введите ID записи для удаления: ");
        int id = Integer.parseInt(scanner.nextLine());
        dbManager.deleteBirthday(id);
        System.out.println("Запись удалена.");
    }

    private static void updateBirthday() {
        System.out.print("Введите ID записи для редактирования: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите новое имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите новую дату (ГГГГ-ММ-ДД): ");
        String date = scanner.nextLine();
        System.out.print("Введите новую заметку: ");
        String note = scanner.nextLine();

        dbManager.updateBirthday(id, name, date, note);
        System.out.println("Запись обновлена.");
    }
}
