package Providers;

import java.util.ArrayList;

public interface GetSequenceAccessMethod<T, TSearch> {
    ArrayList<T> Get(TSearch searchObject);
}