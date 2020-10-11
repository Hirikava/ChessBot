package Providers;

import java.util.Optional;

public interface GetDao<T, TSearch> {
    Optional<T> Get(TSearch searchObject);
}
