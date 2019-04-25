package com.monzo.androidtest.common;

/**
 * Value type to signify a stream event/notification that we are not interested in.
 * i.e. Can be used as a return type for Observable<Object>.
 * NB: If using Kotlin, prefer Unit.
 */
public enum Event {
    IGNORE
}
