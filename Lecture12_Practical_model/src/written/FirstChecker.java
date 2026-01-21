package written;

public class FirstChecker extends Checker {
    @Override
    public boolean check(Section s) {
        String title = s.getTitle();
        String content = s.getContent();
        String firstCharTitle = title.substring(0,1);
        //check it there are at least 2 sentences and the first character of the title is uppercase
        String[] sentences = content.split("[.!?]");
        if(sentences.length >= 2 && firstCharTitle.equals(firstCharTitle.toUpperCase())) {
            return true;
        }
        return false;
    }
}

