public class Birthday {
    private int id;
    private String name;
    private String date;
    private String note;

    public Birthday(int id, String name, String date, String note) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.note = note;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getNote() { return note; }

    @Override
    public String toString() {
        return "ID: " + id + ", Имя: " + name + ", День Рождения: " + date + ", Заметки: " + note;
    }
}
