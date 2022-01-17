package bot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IBaseCrudRepository<E, ID extends String> {


    default void save(E e) {

    }

    default Optional<E> get(ID id) {
        return Optional.empty();
    }

    default Optional<List<E>> getAll(String suffix) {
        return Optional.empty();
    }

    default void updateFirstname(E e) {

    }


    default void updateLastname(E e) {

    }

    default void updateState(E e) {

    }

    default void updateUsername(E e) {

    }

    default void updateLanguage(E e) {

    }

    default void delete(E e) {

    }

    default ArrayList<E> select(String suffix) {
        return null;
    }

    default void updateLoggedIn(E e) {

    }
}
