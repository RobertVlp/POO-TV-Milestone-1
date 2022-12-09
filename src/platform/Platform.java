package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import visitor.Visitable;
import visitor.Visitor;

import java.util.ArrayList;

public final class Platform implements Visitable {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Action> actions;
    private String currentPage;
    private User currentUser;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<Action> actions) {
        this.actions = actions;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void acceptChangePage(
            final Visitor visitor,
            final String destinationPage,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.changePage(destinationPage);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else if ((destinationPage.equals("see details") || destinationPage.equals("movies"))) {
            updateAvailableMovies();
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
        }

        if (destinationPage.equals("logout")) {
            this.setCurrentUser(null);
            this.setCurrentPage("homepage neautentificat");
        }
    }

    @Override
    public void acceptLogin(
            final Visitor visitor,
            final User.Credentials credentials,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.login(credentials);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
        }
    }

    private void updateAvailableMovies() {
        for (Movie movie : movies) {
            boolean isMovieBanned = false;

            for (String country : movie.getCountriesBanned()) {
                if (currentUser.getCredentials().getCountry().equals(country)) {
                    isMovieBanned = true;
                    break;
                }
            }

            if (!isMovieBanned) {
                currentUser.getAvailableMovies().add(movie);
            }
        }
    }

    @Override
    public void acceptRegister(
            final Visitor visitor,
            final User.Credentials credentials,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.register(credentials);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
        }
    }

    /**
     * Adds a user in the platform's database
     * @param credentials for credentials of the new user
     * @return the newly added user
     */
    public User addUser(final User.Credentials credentials) {
        User newUser = new User();
        newUser.setCredentials(credentials);
        users.add(newUser);

        return newUser;
    }

    @Override
    public void acceptSearch(
            final Visitor visitor,
            final String startsWith,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.search(startsWith);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
        }
    }

    @Override
    public void acceptFilter(
            final Visitor visitor,
            final Action.Filters filters,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.filter(filters);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
        }
    }
}
