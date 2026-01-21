package written;

public class Main {
    public static void main(String[] args) {
        FirstChecker checker = new FirstChecker();
        Section section = new Section("Introduction", "This is the introduction content. Amin!", checker);
        section.generate();

        SecondChecker secondChecker = new SecondChecker();
        Section section2 = new Section("Conclusion N", "This is theaaaaaaaaaaaaaaaaaaaaaaaaaaaaa conclusion content which is sufficiently long to pass the second checker's requirements. It contains more than three hundred characters to ensure that it meets the length criteria set by the SecondChecker class. Additionally, the title does not contain any spaces.", secondChecker);
        section2.generate();
    }
}
