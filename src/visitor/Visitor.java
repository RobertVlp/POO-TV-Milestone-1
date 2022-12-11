package visitor;

import platform.Action;
import platform.User;

public interface Visitor {
    String changePage(String destinationPage);
    String login(User.Credentials credentials);
    String register(User.Credentials credentials);
    String search(String startsWith);
    String filter(Action.Filters filters);
    String buyTokens(Integer count);
    String buyPremiumAccount();
    String purchaseMovie();
    String watchMovie();
    String likeMovie();
    String rateMovie(Integer rate);
}
