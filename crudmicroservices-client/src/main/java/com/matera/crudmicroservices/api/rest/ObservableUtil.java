package com.matera.crudmicroservices.api.rest;

import static java.util.Objects.isNull;

import rx.Observable;
import rx.functions.Func1;

/**
 * Utility class to
 *
 * @author egzefer
 */
public class ObservableUtil {

    public static <T> Func1<T, Observable<T>> checkNull() {

        return new Func1<T, Observable<T>>() {

            public Observable<T> call(T obj) {

                if (isNull(obj)) {
                    return Observable.empty();
                }
                return Observable.just(obj);
            }
        };
    }
}
