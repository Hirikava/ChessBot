package Providers;

import java.util.Optional;

public interface GetDataAccessMethod<T, TSearch> {
    Optional<T> Get(TSearch searchObject);
}
