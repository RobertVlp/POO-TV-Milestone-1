package visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jetbrains.annotations.NotNull;
import platform.Action;
import platform.User;

public interface Visitable {
    void acceptChangePage(
            Visitor visitor,
            String destinationPage,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptLogin(
            Visitor visitor,
            User.Credentials credentials,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptRegister(
            Visitor visitor,
            User.Credentials credentials,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptSearch(
            Visitor visitor,
            String startsWith,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    void acceptFilter(
            Visitor visitor,
            Action.Filters filters,
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException;

    default void parseOutput(
            ObjectNode jsonObject,
            ObjectMapper objectMapper,
            String fieldName,
            Object value
    ) throws JsonProcessingException {
        jsonObject.set(
                fieldName,
                objectMapper.readTree(
                        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value)
                )
        );
    }

    default void parseErrorOutput(
            ObjectNode jsonObject,
            ObjectMapper objectMapper
    ) throws JsonProcessingException {
        parseOutput(jsonObject, objectMapper, "error", "Error");
        parseOutput(
                jsonObject,
                objectMapper,
                "currentMoviesList",
                objectMapper.createArrayNode()
        );
        parseOutput(jsonObject, objectMapper, "currentUser", null);
    }

    default void parseSuccessOutput(
            ObjectNode jsonObject,
            ObjectMapper objectMapper,
            @NotNull User currentUser
    ) throws JsonProcessingException {
        parseOutput(jsonObject, objectMapper, "error", null);
        parseOutput(
                jsonObject, objectMapper,
                "currentMoviesList",
                currentUser.getAvailableMovies()
        );
        parseOutput(jsonObject, objectMapper, "currentUser", currentUser);
    }
}
