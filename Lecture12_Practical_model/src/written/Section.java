package written;

public class Section {
    private String title;
    private String content;
    private Checker checker;

    public Section(String title, String content, Checker checker) {
        this.title = title;
        this.content = content;
        this.checker = checker;
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }

    public void generate() {
        if(checker.check(this)) {
            System.out.println("Section generated: " + title);
        } else {
            System.out.println("Section failed checks: " + title);
        }
    }
}
