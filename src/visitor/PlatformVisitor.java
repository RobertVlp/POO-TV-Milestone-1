package visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;

import platform.*;
import platform.movie.Movie;
import platform.movie.SortMoviesComparator;

public final class PlatformVisitor implements Visitor {
    private final Platform platform;

    public PlatformVisitor(final Platform platform) {
        this.platform = platform;
    }

    public void performActions(
            final ObjectMapper objectMapper,
            final ArrayNode output
    ) throws JsonProcessingException {
        for (Action action : platform.getActions()) {
            ObjectNode jsonObject = objectMapper.createObjectNode();

            switch (action.getType()) {
                case "change page" -> {
                    platform.acceptChangePage(this, action.getPage(), jsonObject, objectMapper);

                    if (!jsonObject.isEmpty()) {
                        output.add(jsonObject);
                    }
                }

                case "on page" -> {
                    switch (action.getFeature()) {
                        case "login" ->
                                platform.acceptLogin(
                                    this,
                                    action.getCredentials(),
                                    jsonObject,
                                    objectMapper
                                );
                        case "register" ->
                                platform.acceptRegister(
                                        this,
                                        action.getCredentials(),
                                        jsonObject,
                                        objectMapper
                                );

                        case "search" ->
                                platform.acceptSearch(
                                            this,
                                            action.getStartsWith(),
                                            jsonObject,
                                            objectMapper
                                );

                        case "filter" ->
                                platform.acceptFilter(
                                        this,
                                        action.getFilters(),
                                        jsonObject,
                                        objectMapper
                                );

                        default -> {
                        }
                    }

                    output.add(jsonObject);
                }

                default -> {
                }
            }
        }
    }

    @Override
    public String changePage(final String destinationPage) {
        switch (platform.getCurrentPage()) {
            case "homepage neautentificat" -> {
                if (!destinationPage.equals("login") && !destinationPage.equals("register")) {
                    return "Error";
                }
            }

            case "homepage autentificat" -> {
                if (!destinationPage.equals("movies") && !destinationPage.equals("upgrades")
                        && !destinationPage.equals("logout")) {
                    return "Error";
                }
            }

            case "movies" -> {
                if (!destinationPage.equals("homepage autentificat")
                        && !destinationPage.equals("see details")
                        && !destinationPage.equals("logout")) {
                    return "Error";
                }
            }

            case "upgrades" -> {
                if (!destinationPage.equals("homepage autentificat")
                        && !destinationPage.equals("movies")
                        && !destinationPage.equals("logout")) {
                    return "Error";
                }
            }

            case "see details" -> {
                if (!destinationPage.equals("homepage autentificat")
                        && !destinationPage.equals("movies")
                        && !destinationPage.equals("upgrades")
                        && !destinationPage.equals("logout")) {
                    return "Error";
                }
            }

            default -> {
            }
        }

        platform.setCurrentPage(destinationPage);

        return null;
    }

    @Override
    public String login(final User.Credentials credentials) {
        if (platform.getCurrentUser() != null) {
            return "Error";
        }

        if (!platform.getCurrentPage().equals("login")) {
            return "Error";
        }

        User loggedUser = null;

        for (User user : platform.getUsers()) {
            if (user.getCredentials().getName().equals(credentials.getName())
                    && user.getCredentials().getPassword().equals(credentials.getPassword())) {
                loggedUser = user;
                break;
            }
        }

        if (loggedUser == null) {
            return "Error";
        }

        platform.setCurrentUser(loggedUser);
        platform.setCurrentPage("homepage autentificat");

        return null;
    }

    @Override
    public String register(final User.Credentials credentials) {
        if (!platform.getCurrentPage().equals("register")) {
            return "Error";
        }

        for (User user : platform.getUsers()) {
            if (user.getCredentials().getName().equals(credentials.getName())) {
                return "Error";
            }
        }

        platform.setCurrentUser(platform.addUser(credentials));
        platform.setCurrentPage("homepage autentificat");

        return null;
    }

    @Override
    public String search(final String startsWith) {
        if (!platform.getCurrentPage().equals("movies")) {
            return "Error";
        }

        ArrayList<Movie> movies = platform.getCurrentUser().getAvailableMovies();
        movies.removeIf(movie -> !movie.getName().startsWith(startsWith));

        return null;
    }

    @Override
    public String filter(final Action.Filters filters) {
        if (!platform.getCurrentPage().equals("movies")) {
            return "Error";
        }

        ArrayList<Movie> movies = platform.getCurrentUser().getAvailableMovies();

        if (filters.getSort() != null) {
            String duration = filters.getSort().getDuration();
            String rating = filters.getSort().getRating();
            SortMoviesComparator comparator = new SortMoviesComparator(duration, rating);

            movies.sort(comparator);
        }

        if (filters.getContains() != null) {
            movies.removeIf(
                    movie -> !movie.getActors().containsAll(filters.getContains().getActors())
                                || !movie.getGenres().containsAll(filters.getContains().getGenre())
            );
        }

        return null;
    }
}
