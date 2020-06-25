package br.com.marcia.error.service;

public interface IService<T> {
    T save(T object);
}
