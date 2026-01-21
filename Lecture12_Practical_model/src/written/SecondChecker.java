package written;

public class SecondChecker extends Checker {
    @Override
    public boolean check(Section s) {
        String content = s.getContent();
        if(content.length() >= 300 && !s.getTitle().contains(" ")) {
            return true;
        }
        return false;
    }
}
