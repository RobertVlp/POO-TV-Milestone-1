package visitor;

import platform.Action;
import platform.User;

public interface Visitor {
    String changePage(String destinationPage);
    String login(User.Credentials credentials);
    String register(User.Credentials credentials);
    String search(String startsWith);
    String filter(Action.Filters filters);
}
